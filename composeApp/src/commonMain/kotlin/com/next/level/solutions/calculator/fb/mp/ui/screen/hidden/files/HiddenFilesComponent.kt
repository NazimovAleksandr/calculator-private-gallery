package com.next.level.solutions.calculator.fb.mp.ui.screen.hidden.files

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.next.level.solutions.calculator.fb.mp.data.database.AppDatabase
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.AdsManager
import com.next.level.solutions.calculator.fb.mp.entity.ui.FileDataUI
import com.next.level.solutions.calculator.fb.mp.entity.ui.NoteModelUI
import com.next.level.solutions.calculator.fb.mp.entity.ui.PhotoModelUI
import com.next.level.solutions.calculator.fb.mp.entity.ui.TrashModelUI
import com.next.level.solutions.calculator.fb.mp.expect.PlatformExp
import com.next.level.solutions.calculator.fb.mp.extensions.core.instance
import com.next.level.solutions.calculator.fb.mp.extensions.core.launchIO
import com.next.level.solutions.calculator.fb.mp.extensions.core.launchMain
import com.next.level.solutions.calculator.fb.mp.file.visibility.manager.FileVisibilityManager
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.PickerType
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.PickerMode
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent.Configuration
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent.DialogConfiguration
import com.next.level.solutions.calculator.fb.mp.ui.root.chooseDialog
import com.next.level.solutions.calculator.fb.mp.ui.root.createNotes
import com.next.level.solutions.calculator.fb.mp.ui.root.fileHider
import com.next.level.solutions.calculator.fb.mp.ui.root.lottie
import com.next.level.solutions.calculator.fb.mp.ui.screen.hidden.files.dialog.ChooseDialogComponent
import com.next.level.solutions.calculator.fb.mp.utils.Logger
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Suppress("UnusedReceiverParameter")
class HiddenFilesComponent(
  componentContext: ComponentContext,
  val adsManager: AdsManager,
  private val database: AppDatabase,
  private val fileVisibilityManager: FileVisibilityManager,
  private val navigation: StackNavigation<Configuration>,
  private val dialogNavigation: SlotNavigation<DialogConfiguration>,
) : RootComponent.Child(adsManager), ComponentContext by componentContext {

  private val handler: Handler = instance<Handler>(componentContext)

  private val _model: MutableValue<Model> by lazy { MutableValue(initialModel()) }
  val model: Value<Model> get() = _model

  private val hiddenFilesByDateAdded: StateFlow<ImmutableList<FileDataUI>> by lazy {
    database.fetchByDate(type = handler.fileType).prepare()
      .stateIn(
        scope = coroutineScope(),
        started = SharingStarted.Eagerly,
        initialValue = persistentListOf(),
      )
  }

  private val hiddenFilesByFileSize: StateFlow<ImmutableList<FileDataUI>> by lazy {
    database.fetchBySize(type = handler.fileType).prepare()
      .stateIn(
        scope = coroutineScope(),
        started = SharingStarted.Eagerly,
        initialValue = persistentListOf(),
      )
  }

  private val hiddenFilesByName: StateFlow<ImmutableList<FileDataUI>> by lazy {
    database.fetchByName(type = handler.fileType).prepare()
      .stateIn(
        scope = coroutineScope(),
        started = SharingStarted.Eagerly,
        initialValue = persistentListOf(),
      )
  }

  private var selectedFiles: List<FileDataUI> = emptyList()

  @Composable
  override fun content() {
    HiddenFilesContent(component = this)
  }

  override fun action(action: RootComponent.Child.Action) {
    action.updateModel()

    launchMain {
      action.doSomething()?.let {
        action(it)
      }
    }
  }

  private fun initialModel(): Model {
//    analytics.hiddenFiles.screenOpen(fileType = route.fileType)

    return Model(
      fileType = handler.fileType,
      viewType = handler.viewType,
      files = hiddenFilesByDateAdded,
    )
  }

  private fun RootComponent.Child.Action.updateModel() {
    when (this) {
      is Action.Selected -> update()
      is Action.OpenFilesOpener -> update()
      is Action.CloseFilesOpener -> update()
      is Action.SortByDateAdded -> update()
      is Action.SortByFileSize -> update()
      is Action.SortByName -> update()
    }
  }

  private fun RootComponent.Child.Action.doSomething(): Action? {
    when (this) {
      is Action.Back -> back()
      is Action.LoadNative -> adsManager.native.load()

      is Action.Add -> add()
      is ChooseDialogComponent.Action.Gallery -> gallery()
      is ChooseDialogComponent.Action.Folder -> folder()

      is Action.Visible -> moveToVisibleFiles(selectedFiles)
      is Action.VisibleFile -> moveToVisibleFiles(listOf(file))

      is Action.Delete -> moveToDeletedFiles(selectedFiles)
      is Action.DeleteFile -> moveToDeletedFiles(listOf(file))

      is Action.Restore -> restoreFiles(selectedFiles)
      is Action.RestoreFile -> restoreFiles(listOf(file))
    }

    return null
  }

  private fun Action.Selected.update() {
    selectedFiles = files

    val selectedItemCount = when {
      files.isEmpty() -> 0
      else -> files.size
    }

    _model.update { it.copy(selectedItemCount = selectedItemCount) }
  }

  private fun Action.OpenFilesOpener.update() {
    when (handler.fileType) {
      PickerType.File -> PlatformExp.openFile(file)
      PickerType.Note -> navigation.pushNew(Configuration.createNotes(file as NoteModelUI))

      else -> _model.update { it.copy(openFile = file) }
    }
  }

  private fun Action.CloseFilesOpener.update() {
    _model.update { it.copy(openFile = null) }
  }

  private fun Action.SortByDateAdded.update() {
    _model.update { it.copy(files = hiddenFilesByDateAdded) }
  }

  private fun Action.SortByFileSize.update() {
    _model.update { it.copy(files = hiddenFilesByFileSize) }
  }

  private fun Action.SortByName.update() {
    _model.update { it.copy(files = hiddenFilesByName) }
  }

  private fun back() {
    navigation.pop()
  }

  private fun add() {
//    analytics.hiddenFiles.addButtonClick()

    when (handler.fileType) {
      PickerType.Note -> navigation.pushNew(
        configuration = Configuration.createNotes(null),
      )

      PickerType.Photo -> dialogNavigation.activate(
        DialogConfiguration.chooseDialog(
          fileType = PickerType.Photo,
          open = ::action
        )
      )

      PickerType.Video -> dialogNavigation.activate(
        DialogConfiguration.chooseDialog(
          fileType = PickerType.Video,
          open = ::action
        )
      )

      PickerType.File -> navigation.pushNew(
        Configuration.fileHider(
          fileType = handler.fileType,
          viewType = PickerMode.Folder,
        )
      )

      PickerType.Trash -> {}
    }
  }

  private fun gallery() {
//    analytics.hiddenFiles.galleryOpen()
    toFileHider(viewType = PickerMode.Gallery)
  }

  private fun folder() {
//    analytics.hiddenFiles.folderOpen()
    toFileHider(viewType = PickerMode.Folder)
  }

  private fun toFileHider(
    viewType: PickerMode,
  ) {
    navigation.pushNew(
      Configuration.fileHider(
        fileType = handler.fileType,
        viewType = viewType,
      )
    )
  }

  private fun moveToVisibleFiles(files: List<FileDataUI>) {
//    analytics.hiddenFiles.revealed()

    launchIO {
      delay(500)

      fileVisibilityManager.moveToVisibleFiles(
        files = files,
      ) {
        database.delete(
          type = handler.fileType,
          files = it,
        )
      }
    }

    navigation.pushNew(Configuration.lottie())
  }

  private fun restoreFiles(files: List<FileDataUI>) {
//    analytics.hiddenFiles.revealed()

    launchIO {
      delay(500)

      Logger.d(TAG, "restoreFiles.files: ${files.size}")

      files.forEach {
        Logger.d(TAG, "restoreFiles.file: $it")
      }

      fileVisibilityManager.restoreToInvisibleFiles(
        files = files.filterIsInstance<TrashModelUI>(),
      ) { invisibleFiles ->
        Logger.d(TAG, "restoreToInvisibleFiles: ${invisibleFiles.size}")

        invisibleFiles.forEach {
          Logger.d(TAG, "file: $it")
        }

        val trashByType: Map<PickerType, List<TrashModelUI>> = invisibleFiles
          .groupBy { it.fileType }
        Logger.d(TAG, "trashByType: $trashByType")
        //trashByType: {Trash=[TrashModelUI(path=/storage/emulated/0/IMG-20250306-WA0005.jpg, name=IMG-20250306-WA0005, folder=0, size=88057, dateAdded=1741356650, dateHidden=19_13_08_000, dateModified=1741271863, hiddenPath=/storage/emulated/0/Documents/.HiddenDirectory_Calculator/Photo/(19_13_08_000)IMG-20250306-WA0005.jpg)]}

        trashByType[PickerType.File]
          ?.map { it.toFileUI() }
          ?.let { database.insert(type = PickerType.File, files = it) }

        trashByType[PickerType.Note]
          ?.map { it.toNoteUI() }
          ?.let { database.insert(type = PickerType.Note, files = it) }

        trashByType[PickerType.Photo]
          ?.map { it.toPhotoUI() }
          ?.let { database.insert(type = PickerType.Photo, files = it) }

        trashByType[PickerType.Video]
          ?.map { it.toVideoUI() }
          ?.let { database.insert(type = PickerType.Video, files = it) }

        database.delete(
          type = PickerType.Trash,
          files = invisibleFiles
        )
      }
    }

    navigation.pushNew(Configuration.lottie())
  }

  private fun moveToDeletedFiles(files: List<FileDataUI>) {
//    analytics.hiddenFiles.deleted()

    launchIO {
      delay(500)

      fileVisibilityManager.moveToDeletedFiles(
        fileType = handler.fileType,
        files = files,
      ) {
        if (handler.fileType != PickerType.Trash) {
          database.insert(
            type = PickerType.Trash,
            files = it,
          )
        }

        database.delete(
          type = handler.fileType,
          files = it,
        )
      }
    }

    navigation.pushNew(Configuration.lottie())
  }

  private fun Flow<List<FileDataUI>>.prepare(): Flow<ImmutableList<FileDataUI>> = map {
    when (handler.fileType) {
      PickerType.Note -> it

      else -> {
        val files = fileVisibilityManager.invisibleFiles(fileType = handler.fileType)

        files.forEach {
          Logger.d(TAG, "invisibleFiles: $it")
        }

        Logger.d(TAG, "DB_Size: ${it.size}")

        it.filter { fileDataUI ->
          Logger.d(TAG, "fileDataUI: $fileDataUI")
          files.any { file ->
            file.name.contains("(${fileDataUI.dateHidden})${fileDataUI.name}").apply {
              when {
                this && fileDataUI is PhotoModelUI && file is PhotoModelUI -> fileDataUI.url = file.url
              }
            }
          }
        }
      }
    }.toImmutableList()
  }

  /**
   * Component contract - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
   */
  class Handler(
    val fileType: PickerType,
    val viewType: PickerMode,
  ) : InstanceKeeper.Instance

  data class Model(
    val fileType: PickerType,
    val viewType: PickerMode,
    val files: StateFlow<ImmutableList<FileDataUI>>,
    val openFile: FileDataUI? = null,
    val selectedItemCount: Int = 0,
  )

  sealed interface Action : RootComponent.Child.Action {
    object Add : Action
    object Visible : Action
    object Restore : Action
    object Delete : Action
    object Back : Action

    object SortByDateAdded : Action
    object SortByFileSize : Action
    object SortByName : Action

    object LoadNative : Action

    class Selected(val files: List<FileDataUI>) : Action
    class OpenFilesOpener(val file: FileDataUI) : Action
    class VisibleFile(val file: FileDataUI) : Action
    class RestoreFile(val file: FileDataUI) : Action
    class DeleteFile(val file: FileDataUI) : Action
    object CloseFilesOpener : Action
  }
}