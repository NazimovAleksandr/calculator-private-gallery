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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.next.level.solutions.calculator.fb.mp.entity.ui.FileDataUI
import com.next.level.solutions.calculator.fb.mp.entity.ui.PhotoModelUI
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

enum class PickerAction { Click, Select }
enum class PickerMode { Folder, Gallery }
enum class PickerType { File, Note, Photo, Trash, Video }

class PickerState(
  action: PickerAction,
  mode: PickerMode,
) {
  internal val allSelectReloadState: MutableState<Int> = mutableIntStateOf(1)
  internal val allSelectState: MutableState<Boolean> = mutableStateOf(false)

  val mode: State<PickerMode> = mutableStateOf(mode)
  val action: State<PickerAction> = mutableStateOf(action)

  fun allSelect(value: Boolean) {
    val reload = allSelectState.value == value

    when (reload) {
      true -> allSelectReloadState.value += 1
      else -> allSelectState.value = value
    }
  }

  fun setAction(value: PickerAction) {
    (this@PickerState.action as MutableState).value = value
  }
}

@Composable
fun rememberFilePickerState(
  initMode: PickerMode = PickerMode.Gallery,
  initAction: PickerAction = PickerAction.Click,
): PickerState {
  return remember {
    PickerState(
      mode = initMode,
      action = initAction,
    )
  }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun FilePicker(
  state: PickerState = rememberFilePickerState(),
  files: State<ImmutableList<FileDataUI>>,
  modifier: Modifier = Modifier,
  contentSpace: Dp = 2.dp,
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
    contentSpace = contentSpace,
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
      initAction = PickerAction.Select,
    ),
    files = remember { mutableStateOf(list) },
  )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun Content(
  files: State<ImmutableList<FileDataUI>>,
  modifier: Modifier = Modifier,
  state: PickerState = rememberFilePickerState(),
  contentSpace: Dp = 2.dp,
  contentPadding: PaddingValues = PaddingValues(0.dp),
  sharedTransitionScope: SharedTransitionScope? = null,
  animatedVisibilityScope: AnimatedVisibilityScope? = null,
  onClick: (FileDataUI) -> Unit = {},
  onSelect: (List<FileDataUI>) -> Unit = {},
) {
  val currentViewType by remember { state.mode }
  val currentMode by remember { state.action }

  val selectedFiles: MutableState<List<FileDataUI>> = remember { mutableStateOf(listOf()) }

  val allSelectState by remember { state.allSelectState }
  val allSelectReloadState by remember { state.allSelectReloadState }

  val haptic = LocalHapticFeedback.current

  fun onLongClick(file: FileDataUI) {
    if (currentMode != PickerAction.Select) {
      haptic.performHapticFeedback(HapticFeedbackType.LongPress)

      (state.action as MutableState).value = PickerAction.Select

      val sFiles = selectedFiles.value + file

      onSelect(sFiles)
      selectedFiles.value = sFiles
    }
  }

  fun onTap(file: FileDataUI) {
    when (currentMode) {
      PickerAction.Click -> onClick(file)

      PickerAction.Select -> {
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
    PickerMode.Gallery -> Gallery(
      modifier = modifier,
      files = files,
      contentSpace = contentSpace,
      contentPadding = contentPadding,
      currentMode = state.action,
      selectedFiles = selectedFiles,
      sharedTransitionScope = sharedTransitionScope,
      animatedVisibilityScope = animatedVisibilityScope,
      onLongClick = ::onLongClick,
      onTap = ::onTap,
    )

    PickerMode.Folder -> Folder(
      modifier = modifier,
      files = files,
      contentSpace = contentSpace,
      contentPadding = contentPadding,
      currentMode = state.action,
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
    if (currentMode == PickerAction.Click) {
      selectedFiles.value = emptyList()
    }
  }
}