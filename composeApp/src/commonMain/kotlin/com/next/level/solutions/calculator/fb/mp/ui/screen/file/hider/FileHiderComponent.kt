package com.next.level.solutions.calculator.fb.mp.ui.screen.file.hider

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.next.level.solutions.calculator.fb.mp.constants.ALL_FILES
import com.next.level.solutions.calculator.fb.mp.data.database.AppDatabase
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.AdsManager
import com.next.level.solutions.calculator.fb.mp.entity.ui.FileDataUI
import com.next.level.solutions.calculator.fb.mp.entity.ui.FolderModelUI
import com.next.level.solutions.calculator.fb.mp.entity.ui.ParentFolderModelUI
import com.next.level.solutions.calculator.fb.mp.entity.ui.toPath
import com.next.level.solutions.calculator.fb.mp.extensions.core.instance
import com.next.level.solutions.calculator.fb.mp.extensions.core.launchIO
import com.next.level.solutions.calculator.fb.mp.extensions.core.launchMain
import com.next.level.solutions.calculator.fb.mp.file.visibility.manager.FileVisibilityManager
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.PickerType
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.PickerMode
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent.Configuration
import com.next.level.solutions.calculator.fb.mp.ui.root.lottie
import com.next.level.solutions.calculator.fb.mp.utils.Logger
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

class FileHiderComponent(
  componentContext: ComponentContext,
  adsManager: AdsManager,
  private val database: AppDatabase,
  private val fileVisibilityManager: FileVisibilityManager,
  private val navigation: StackNavigation<Configuration>,
) : RootComponent.Child(adsManager), ComponentContext by componentContext {

  private val all: String = ALL_FILES
  private var selectedFiles: List<FileDataUI> = emptyList()

  private val handler: Handler = instance<Handler>(componentContext)

  private val visibleFiles: Map<String, List<FileDataUI>> = fileVisibilityManager.visibleFiles(
    fileType = handler.fileType,
    viewType = handler.viewType,
  ).toMap()

  private val _model: MutableValue<Model> by lazy { MutableValue(initialModel()) }
  val model: Value<Model> get() = _model

  @Composable
  override fun content() {
    FileHiderContent(component = this)
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
    val allFiles: ImmutableList<FileDataUI> = visibleFiles.getAllFiles()

    val folders: ImmutableList<FileDataUI> = when (handler.viewType) {
      PickerMode.Gallery -> allFiles.getAllFolders()
      PickerMode.Folder -> listOf(visibleFiles.getParentFolder())
    }.filterNotNull()
      .toImmutableList()

    val currentFolder = when (handler.viewType) {
      PickerMode.Gallery -> null
      PickerMode.Folder -> visibleFiles.getParentFolder()
    }

    return Model(
      fileType = handler.fileType,
      viewType = handler.viewType,
      files = allFiles,
      folders = folders,
      selectedFolder = folders.firstOrNull() ?: all.toFolder(),
      currentFolder = currentFolder,
    )
  }

  private fun RootComponent.Child.Action.updateModel() {
    when (this) {
      is Action.SelectFolder -> update()
      is Action.Selected -> update()
      is Action.SetFiles -> update()
    }
  }

  private fun RootComponent.Child.Action.doSomething(): Action? {
    when (this) {
      is Action.LoadFiles -> load()
      is Action.Hide -> moveToHiddenFiles()
      is Action.Back -> back()
    }

    return null
  }

  private fun Action.SelectFolder.update() {
    _model.update {
      when (handler.viewType) {
        PickerMode.Gallery -> it.copy(
          files = visibleFiles.getAllFiles(folder.name),
          selectedFolder = folder,
        )

        PickerMode.Folder -> if (it.currentFolder?.path != folder.path) {
          action(Action.LoadFiles(path = folder.path))
          it.copy(selectedItemCount = 0)
        } else {
          it
        }
      }
    }
  }

  private fun Action.Selected.update() {
    val folder = files.firstOrNull { it is FolderModelUI }

    _model.update {
      when (folder) {
        null -> {
          selectedFiles = files

          selectedFiles.forEach {
            Logger.d("FileHiderComponent", "Selected.update: $it")
          }

          it.copy(
            selectedItemCount = files.size,
          )
        }

        else -> {
          action(Action.LoadFiles(path = folder.path))

          it.copy(
            selectedItemCount = 0,
          )
        }
      }
    }
  }

  private fun Action.SetFiles.update() {
    _model.update {
      it.copy(
        files = files,
        folders = folders,
        currentFolder = currentFolder,
      )
    }
  }

  private fun moveToHiddenFiles() {
//    analytics.fileHider.hiddenSuccess(fileType = route.fileType)

    launchIO(
      scope = CoroutineScope(Job()),
    ) {
      delay(500)

      fileVisibilityManager.moveToInvisibleFiles(
        fileType = handler.fileType,
        files = selectedFiles,
      ) {
        database.insert(
          type = handler.fileType,
          files = it,
        )
      }
    }

    navigation.replaceCurrent(Configuration.lottie())
  }

  private fun back() {
    when (handler.viewType) {
      PickerMode.Gallery -> launchMain { navigation.pop() }

      else -> {
        when (_model.value.currentFolder == visibleFiles.getParentFolder()) {
          true -> launchMain { navigation.pop() }

          else -> _model.value.currentFolder?.let { currentFolder ->
            action(Action.LoadFiles(path = currentFolder.path, fromParent = true))
          }
        }
      }
    }
  }

  private fun Action.LoadFiles.load() {
    launchIO {
      fileVisibilityManager.visibleFiles(
        folder = path,
        fromParent = fromParent,
        fileType = handler.fileType,
        callBack = { visibleFiles ->
          val currentFolder = visibleFiles.folder()
          val folders = currentFolder.toPath(
            this@FileHiderComponent.visibleFiles.getParentFolder()?.path
          ).toImmutableList()

          action(
            Action.SetFiles(
              files = visibleFiles.files(),
              folders = folders,
              currentFolder = currentFolder,
            )
          )
        }
      )
    }
  }

  private fun List<FileDataUI>.toMap(): Map<String, List<FileDataUI>> = let { allFiles ->
    allFiles
      .groupBy { file -> file.folder }
      .toMutableMap()
      .apply { put(all, allFiles) }
  }

  private fun Map<String, List<FileDataUI>>.getAllFiles(folder: String = all): ImmutableList<FileDataUI> {
    return getOrElse(folder) { emptyList() }.filter { it !is ParentFolderModelUI }.toImmutableList()
  }

  private fun Map<String, List<FileDataUI>>.getParentFolder(folder: String = all): FileDataUI? {
    return getOrElse(folder) { emptyList() }.firstOrNull { it is ParentFolderModelUI }
  }

  private fun ImmutableList<FileDataUI>.getAllFolders(): List<ParentFolderModelUI> {
    return listOf(
      all,
      *this.map { it.folder }
        .toSet()
        .sorted()
        .toTypedArray()
    )
      .map {
        ParentFolderModelUI(
          path = "",
          name = it,
        ).apply {
          fileType = handler.fileType
        }
      }
  }

  private fun List<FileDataUI>.files(): ImmutableList<FileDataUI> {
    return filter { it !is ParentFolderModelUI }.toImmutableList()
  }

  private fun List<FileDataUI>.folder(): FileDataUI? {
    return firstOrNull { it is ParentFolderModelUI }
  }

//  private fun FileDataUI?.toPath(): List<FileDataUI> {
//    this ?: return emptyList()
//
//    val files: MutableList<File> = mutableListOf()
//
//    val root = File( ?: "").parentFile
//    var parentFile: File? = File(path)
//
//    while (
//      parentFile != null
//      && parentFile.absolutePath != root?.absolutePath
//    ) {
//      files.add(parentFile)
//      parentFile = parentFile.parentFile
//    }
//
//    return files.map {
//      ParentFolderModelUI(
//        path = it.absolutePath,
//        name = it.name,
//      )
//    }.reversed()
//  }

  private fun String.toFolder() = ParentFolderModelUI(
    path = "",
    name = this,
  ).apply {
    fileType = handler.fileType
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
    val files: ImmutableList<FileDataUI>,
    val folders: ImmutableList<FileDataUI>,
    val selectedFolder: FileDataUI,
    val currentFolder: FileDataUI? = null,
    val selectedItemCount: Int = 0,
  )

  sealed interface Action : RootComponent.Child.Action {
    class Selected(val files: List<FileDataUI>) : Action
    class SelectFolder(val folder: FileDataUI) : Action

    class LoadFiles(val path: String?, val fromParent: Boolean = false) : Action

    class SetFiles(
      val files: ImmutableList<FileDataUI>,
      val folders: ImmutableList<FileDataUI>,
      val currentFolder: FileDataUI?,
    ) : Action

    object Hide : Action
    object Back : Action
  }
}