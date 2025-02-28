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
import com.next.level.solutions.calculator.fb.mp.expect.PlatformExp
import com.next.level.solutions.calculator.fb.mp.extensions.core.instance
import com.next.level.solutions.calculator.fb.mp.extensions.core.launchIO
import com.next.level.solutions.calculator.fb.mp.extensions.core.launchMain
import com.next.level.solutions.calculator.fb.mp.file.hider.FileHider
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.FilePickerFileType
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.FilePickerViewType
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent.Configuration
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent.DialogConfiguration
import com.next.level.solutions.calculator.fb.mp.ui.root.chooseDialog
import com.next.level.solutions.calculator.fb.mp.ui.root.createNotes
import com.next.level.solutions.calculator.fb.mp.ui.root.fileHider
import com.next.level.solutions.calculator.fb.mp.ui.root.lottie
import com.next.level.solutions.calculator.fb.mp.ui.screen.hidden.files.dialog.ChooseDialogComponent
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
  private val fileHider: FileHider,
  private val navigation: StackNavigation<Configuration>,
  private val dialogNavigation: SlotNavigation<DialogConfiguration>,
) : RootComponent.Child(adsManager), ComponentContext by componentContext {

  private val handler: Handler = instance<Handler>(componentContext)

  private val _model: MutableValue<Model> by lazy { MutableValue(initialModel()) }
  val model: Value<Model> get() = _model

  private val hiddenFilesByDateAdded: StateFlow<ImmutableList<FileDataUI>> = database.getFormDatabaseByDateAdded(
    fileType = handler.fileType,
  )
    .prepare()
    .stateIn(
      scope = coroutineScope(),
      started = SharingStarted.Eagerly,
      initialValue = persistentListOf(),
    )

  private val hiddenFilesByFileSize: StateFlow<ImmutableList<FileDataUI>> = database.getFormDatabaseByFileSize(
    fileType = handler.fileType,
  )
    .prepare()
    .stateIn(
      scope = coroutineScope(),
      started = SharingStarted.Eagerly,
      initialValue = persistentListOf(),
    )

  private val hiddenFilesByName: StateFlow<ImmutableList<FileDataUI>> = database.getFormDatabaseByName(
    fileType = handler.fileType,
  )
    .prepare()
    .stateIn(
      scope = coroutineScope(),
      started = SharingStarted.Eagerly,
      initialValue = persistentListOf(),
    )

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
      FilePickerFileType.File -> PlatformExp.openFile(file)
      FilePickerFileType.Note -> navigation.pushNew(Configuration.createNotes(file as NoteModelUI))

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
      FilePickerFileType.Note -> navigation.pushNew(
        configuration = Configuration.createNotes(null),
      )

      FilePickerFileType.Photo -> dialogNavigation.activate(
        DialogConfiguration.chooseDialog(
          fileType = FilePickerFileType.Photo,
          open = ::action
        )
      )

      FilePickerFileType.Video -> dialogNavigation.activate(
        DialogConfiguration.chooseDialog(
          fileType = FilePickerFileType.Video,
          open = ::action
        )
      )

      FilePickerFileType.File -> navigation.pushNew(
        Configuration.fileHider(
          fileType = handler.fileType,
          viewType = FilePickerViewType.Folder,
        )
      )

      FilePickerFileType.Trash -> {}
    }
  }

  private fun gallery() {
//    analytics.hiddenFiles.galleryOpen()
    navigation.pushNew(Configuration.fileHider(fileType = handler.fileType, viewType = FilePickerViewType.Gallery))
  }

  private fun folder() {
//    analytics.hiddenFiles.folderOpen()
    navigation.pushNew(Configuration.fileHider(fileType = handler.fileType, viewType = FilePickerViewType.Folder))
  }

  private fun moveToVisibleFiles(files: List<FileDataUI>) {
//    analytics.hiddenFiles.revealed()

    launchIO {
      delay(500)

      fileHider.moveToVisibleFiles(
        files = files,
      ).let {
        database.deleteFromDatabase(
          fileType = handler.fileType,
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

      fileHider.restoreFiles(
        files = files,
      ).let {
        database.restoreFiles(
          files = it,
        )
      }
    }

    navigation.pushNew(Configuration.lottie())
  }

  private fun moveToDeletedFiles(files: List<FileDataUI>) {
//    analytics.hiddenFiles.deleted()

    launchIO {
      delay(500)

      fileHider.moveToDeletedFiles(
        fileType = handler.fileType,
        files = files,
      ).let {
        database.moveToTrashDB(
          fileType = handler.fileType,
          files = it,
        )
      }
    }

    navigation.pushNew(Configuration.lottie())
  }

  private fun Flow<List<FileDataUI>>.prepare(): Flow<ImmutableList<FileDataUI>> = map {
    when (handler.fileType) {
      FilePickerFileType.Note -> it

      else -> {
        val files = fileHider.hiddenFiles(fileType = handler.fileType)

        it.filter { fileDataUI ->
          files.any { file ->
            file.name.contains("(${fileDataUI.dateHidden})${fileDataUI.name}")
          }
        }
      }
    }.toImmutableList()
  }

  /**
   * Component contract - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
   */
  class Handler(
    val fileType: FilePickerFileType,
    val viewType: FilePickerViewType,
  ) : InstanceKeeper.Instance

  data class Model(
    val fileType: FilePickerFileType,
    val viewType: FilePickerViewType,
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