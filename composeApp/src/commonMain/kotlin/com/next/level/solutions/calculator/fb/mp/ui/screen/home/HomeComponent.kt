package com.next.level.solutions.calculator.fb.mp.ui.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.next.level.solutions.calculator.fb.mp.data.database.AppDatabase
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.AdsManager
import com.next.level.solutions.calculator.fb.mp.entity.ui.FileDataUI
import com.next.level.solutions.calculator.fb.mp.expect.externalStoragePermissionGranted
import com.next.level.solutions.calculator.fb.mp.expect.requestExternalStoragePermission
import com.next.level.solutions.calculator.fb.mp.extensions.core.launchIO
import com.next.level.solutions.calculator.fb.mp.extensions.core.launchMain
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.FilePickerFileType
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.FilePickerViewType
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent.Configuration
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent.DialogConfiguration
import com.next.level.solutions.calculator.fb.mp.ui.root.hiddenFiles
import com.next.level.solutions.calculator.fb.mp.ui.root.needAccessDialog
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentHashMapOf
import kotlinx.collections.immutable.toPersistentMap
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Stable
class HomeComponent(
  componentContext: ComponentContext,
  database: AppDatabase,
  private val adsManager: AdsManager,
  private val navigation: StackNavigation<Configuration>,
  private val dialogNavigation: SlotNavigation<DialogConfiguration>,
) : RootComponent.Child(adsManager), ComponentContext by componentContext {

  init {
    val root = instanceKeeper.get("RootComponent") as? RootComponent
    root?.action(RootComponent.Action.ActivateCollapseSecurity)
  }

  private val hiddenFilesTrash: Flow<List<FileDataUI>> = database.getFormDatabaseByDateAdded(
    fileType = FilePickerFileType.Trash,
  )

  private val hiddenFilesPhoto: Flow<List<FileDataUI>> = database.getFormDatabaseByDateAdded(
    fileType = FilePickerFileType.Photo,
  )

  private val hiddenFilesVideo: Flow<List<FileDataUI>> = database.getFormDatabaseByDateAdded(
    fileType = FilePickerFileType.Video,
  )

  private val hiddenFilesFile: Flow<List<FileDataUI>> = database.getFormDatabaseByDateAdded(
    fileType = FilePickerFileType.File,
  )

  private val hiddenFilesNote: Flow<List<FileDataUI>> = database.getFormDatabaseByDateAdded(
    fileType = FilePickerFileType.Note,
  )

  private var hiddenFilesCount: ImmutableMap<FilePickerFileType, Int> = persistentHashMapOf()

  init {
    updateHiddenFilesCount()
  }

  override fun content(): @Composable () -> Unit = {
    HomeContent(component = this)
  }

  override fun action(action: RootComponent.Child.Action) {
    launchMain {
      action.doSomething()?.let {
        action(it)
      }
    }
  }

  private fun RootComponent.Child.Action.doSomething(): Action? {
    checkExternalStorage() ?: return null

    when (this) {
      is Action.Ad -> adsManager.inter.show {}
//      is Action.SetAdState -> adState = event.state
//      is Action.OnStart -> checkFiles(event.context)
//      is Action.Settings -> navigation.pushNew(Configuration.settings()) todo

      else -> navigateOrShowInter()
    }

    return null
  }

  private fun RootComponent.Child.Action.checkExternalStorage(): Boolean? {
    val result = when (this) {
      is Action.Trash -> externalStoragePermissionGranted()
      is Action.Photos -> externalStoragePermissionGranted()
      is Action.Videos -> externalStoragePermissionGranted()
      is Action.Files -> externalStoragePermissionGranted()

      else -> true
    }

    return when (result) {
      true -> true

      else -> {
//        analytics.home.mediaPermissionRequest()
        dialogNavigation.activate(DialogConfiguration.needAccessDialog(::requestExternalStoragePermission))
        null
      }
    }
  }

  private fun RootComponent.Child.Action.navigateOrShowInter() {
    when (this) {
      is Action.Trash -> FilePickerFileType.Trash.navigateOrShowInter()
      is Action.Photos -> FilePickerFileType.Photo.navigateOrShowInter()
      is Action.Videos -> FilePickerFileType.Video.navigateOrShowInter()
      is Action.Files -> FilePickerFileType.File.navigateOrShowInter()
      is Action.Notes -> FilePickerFileType.Note.navigateOrShowInter()
//      is Action.Browser -> navigateOrShowInter()//  todo Browser
    }
  }

  private fun FilePickerFileType.navigateOrShowInter() {
    val interOffCallback = {
      when (this) {
        FilePickerFileType.Trash,
        FilePickerFileType.Photo,
        FilePickerFileType.Video,
          -> navigation.pushNew(
          Configuration.hiddenFiles(
            fileType = this,
            viewType = FilePickerViewType.Gallery,
            hiddenFilesCount = hiddenFilesCount.getOrElse(this) { 0 }
          )
        )

        FilePickerFileType.File,
        FilePickerFileType.Note,
          -> navigation.pushNew(
          Configuration.hiddenFiles(
            fileType = this,
            viewType = FilePickerViewType.Folder,
            hiddenFilesCount = hiddenFilesCount.getOrElse(this) { 0 }
          )
        )
      }
    }

    when {
//      trash() -> interOffCallback()
//      interIsEnabledByFirstClick() && showInterByFirstClick() -> showInter()
//      showInterByNClick() -> showInter()
      else -> interOffCallback()
    }
  }

  private fun updateHiddenFilesCount() {
    hiddenFilesTrash.collect(FilePickerFileType.Trash)
    hiddenFilesPhoto.collect(FilePickerFileType.Photo)
    hiddenFilesVideo.collect(FilePickerFileType.Video)
    hiddenFilesFile.collect(FilePickerFileType.File)
    hiddenFilesNote.collect(FilePickerFileType.Note)
  }

  private fun Flow<List<FileDataUI>>.collect(fileType: FilePickerFileType) {
    launchIO {
      onEach { files ->
        if (hiddenFilesCount[fileType] != files.size) {
          hiddenFilesCount = hiddenFilesCount.update(fileType, files.size)
        }
      }.collect()
    }
  }

  private fun <K, V> Map<K, V>.update(key: K, valeu: V): PersistentMap<K, V> = this
    .toMutableMap()
    .also { it[key] = valeu }
    .toPersistentMap()

  /**
   * Component contract - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
   */
  class Handler : InstanceKeeper.Instance

  sealed interface Action : RootComponent.Child.Action {
    object Settings : Action
    object Browser : Action
    object Ad : Action
    object Notes : Action
    object Trash : Action
    object Photos : Action
    object Videos : Action
    object Files : Action
//    object OnStart : Action
//    class SetAdState(val state: AdState) : Action
  }
}