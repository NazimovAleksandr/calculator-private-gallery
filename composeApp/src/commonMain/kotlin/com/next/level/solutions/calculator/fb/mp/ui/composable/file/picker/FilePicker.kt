package com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import com.next.level.solutions.calculator.fb.mp.entity.ui.FileDataUI
import com.next.level.solutions.calculator.fb.mp.entity.ui.PhotoModelUI
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

enum class FilePickerMode { Click, Select }
enum class FilePickerViewType { Gallery, Folder }
enum class FilePickerFileType { Trash, Photo, Video, File, Note }

class FilePickerState(
  viewType: FilePickerViewType,
  mode: FilePickerMode,
) {
  internal val allSelectReloadState: MutableState<Int> = mutableIntStateOf(1)
  internal val allSelectState: MutableState<Boolean> = mutableStateOf(false)

  val viewType: State<FilePickerViewType> = mutableStateOf(viewType)
  val mode: State<FilePickerMode> = mutableStateOf(mode)

  fun allSelect(value: Boolean) {
    val reload = allSelectState.value == value

    when (reload) {
      true -> allSelectReloadState.value += 1
      else -> allSelectState.value = value
    }
  }

  fun setMode(value: FilePickerMode) {
    (mode as MutableState).value = value
  }
}

@Composable
fun rememberFilePickerState(
  viewType: FilePickerViewType = FilePickerViewType.Gallery,
  initMode: FilePickerMode = FilePickerMode.Click,
): FilePickerState {
  return remember {
    FilePickerState(
      viewType = viewType,
      mode = initMode,
    )
  }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun FilePicker(
  state: FilePickerState = rememberFilePickerState(),
  files: State<ImmutableList<FileDataUI>>,
  modifier: Modifier = Modifier,
  contentPadding: PaddingValues = PaddingValues(0.dp),
  sharedTransitionScope: SharedTransitionScope? = null,
  animatedVisibilityScope: AnimatedVisibilityScope? = null,
  onClick: (FileDataUI) -> Unit,
  onSelect: (List<FileDataUI>) -> Unit,
) {
  Content(
    state = state,
    files = files,
    modifier = modifier,
    sharedTransitionScope = sharedTransitionScope,
    animatedVisibilityScope = animatedVisibilityScope,
    contentPadding = contentPadding,
    onClick = onClick,
    onSelect = onSelect,
  )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun FilePickerPreview() {
  val list = mutableListOf<PhotoModelUI>().apply {
    repeat(10) {
      add(
        PhotoModelUI(
          path = "$it/Corrin",
          name = "PhotoModelUI",
          folder = "Kassy",
          size = 8933L,
          dateAdded = "1733156968",
          dateHidden = "Dec 04, 2024",
          dateModified = "1733156968",
        )
      )
    }
  }.toImmutableList()

  Content(
    state = rememberFilePickerState(
      initMode = FilePickerMode.Select,
    ),
    files = remember { mutableStateOf(list) },
  )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun Content(
  files: State<ImmutableList<FileDataUI>>,
  modifier: Modifier = Modifier,
  state: FilePickerState = rememberFilePickerState(),
  contentPadding: PaddingValues = PaddingValues(0.dp),
  sharedTransitionScope: SharedTransitionScope? = null,
  animatedVisibilityScope: AnimatedVisibilityScope? = null,
  onClick: (FileDataUI) -> Unit = {},
  onSelect: (List<FileDataUI>) -> Unit = {},
) {
  val currentViewType by remember { state.viewType }
  val currentMode by remember { state.mode }

  val selectedFiles: MutableState<List<FileDataUI>> = remember { mutableStateOf(listOf()) }

  val allSelectState by remember { state.allSelectState }
  val allSelectReloadState by remember { state.allSelectReloadState }

  val haptic = LocalHapticFeedback.current

  fun onLongClick(file: FileDataUI) {
    if (currentMode != FilePickerMode.Select) {
      haptic.performHapticFeedback(HapticFeedbackType.LongPress)

      (state.mode as MutableState).value = FilePickerMode.Select

      val sFiles = selectedFiles.value + file

      onSelect(sFiles)
      selectedFiles.value = sFiles
    }
  }

  fun onTap(file: FileDataUI) {
    when (currentMode) {
      FilePickerMode.Click -> onClick(file)

      FilePickerMode.Select -> {
        val selected = selectedFiles.value.contains(file)

        val sFiles = when (selected) {
          true -> selectedFiles.value - file
          else -> selectedFiles.value + file
        }

        onSelect(sFiles)
        selectedFiles.value = sFiles
      }
    }
  }

  when (currentViewType) {
    FilePickerViewType.Gallery -> Gallery(
      modifier = modifier,
      files = files,
      contentPadding = contentPadding,
      currentMode = state.mode,
      selectedFiles = selectedFiles,
      sharedTransitionScope = sharedTransitionScope,
      animatedVisibilityScope = animatedVisibilityScope,
      onLongClick = ::onLongClick,
      onTap = ::onTap,
    )

    FilePickerViewType.Folder -> Folder(
      modifier = modifier,
      files = files,
      contentPadding = contentPadding,
      currentMode = state.mode,
      selectedFiles = selectedFiles,
      onLongClick = ::onLongClick,
      onTap = ::onTap,
    )
  }

  LaunchedEffect(
    key1 = files.value,
    key2 = allSelectState,
    key3 = allSelectReloadState,
  ) {
    val sFiles = when {
      allSelectState -> files.value
      else -> emptyList()
    }

    onSelect(sFiles.toList())
    selectedFiles.value = sFiles
  }

  LaunchedEffect(
    key1 = currentMode,
  ) {
    if (currentMode == FilePickerMode.Click) {
      selectedFiles.value = emptyList()
    }
  }
}