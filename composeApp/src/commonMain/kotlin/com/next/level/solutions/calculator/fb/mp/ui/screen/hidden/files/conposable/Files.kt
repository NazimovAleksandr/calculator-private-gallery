package com.next.level.solutions.calculator.fb.mp.ui.screen.hidden.files.conposable

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import calculator_fileblocking.composeapp.generated.resources.Res
import calculator_fileblocking.composeapp.generated.resources.add_file
import calculator_fileblocking.composeapp.generated.resources.add_new_notes
import calculator_fileblocking.composeapp.generated.resources.add_photo
import calculator_fileblocking.composeapp.generated.resources.add_videos
import calculator_fileblocking.composeapp.generated.resources.delete
import calculator_fileblocking.composeapp.generated.resources.empty
import calculator_fileblocking.composeapp.generated.resources.restore
import calculator_fileblocking.composeapp.generated.resources.visible
import com.magiccalculatorlock.ui.screens.hidden.files.conposable.EmptyList
import com.next.level.solutions.calculator.fb.mp.entity.ui.FileDataUI
import com.next.level.solutions.calculator.fb.mp.entity.ui.PhotoModelUI
import com.next.level.solutions.calculator.fb.mp.ui.composable.action.button.ActionButton
import com.next.level.solutions.calculator.fb.mp.ui.composable.action.button.ButtonColors
import com.next.level.solutions.calculator.fb.mp.ui.composable.action.button.IconSize
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.FilePicker
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.PickerType
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.PickerAction
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.PickerState
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.PickerMode
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.rememberFilePickerState
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Delete
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Plus
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Restore
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Visible
import com.next.level.solutions.calculator.fb.mp.ui.screen.hidden.files.HiddenFilesComponent
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun ColumnScope.Files(
  pickerState: PickerState,
  fileType: PickerType,
  files: State<ImmutableList<FileDataUI>>?,
  modifierEmptyList: Modifier = Modifier,
  modifierFilePicker: Modifier = Modifier,
  sharedTransitionScope: SharedTransitionScope?,
  animatedVisibilityScope: AnimatedVisibilityScope?,
  action: (HiddenFilesComponent.Action) -> Unit,
) {
  Content(
    pickerState = pickerState,
    fileType = fileType,
    files = files,
    modifierEmptyList = modifierEmptyList,
    modifierFilePicker = modifierFilePicker,
    sharedTransitionScope = sharedTransitionScope,
    animatedVisibilityScope = animatedVisibilityScope,
    action = action,
  )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun FilesPreview(
  modifier: Modifier = Modifier,
  initMode: PickerAction = PickerAction.Click,
) {
  val type = isSystemInDarkTheme()

  val files = remember {
    mutableStateOf(
      when (!type) {
        true -> persistentListOf()
        else -> mutableListOf<PhotoModelUI>().apply {
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
      }
    )
  }

  Column(
    verticalArrangement = Arrangement.Top,
    horizontalAlignment = Alignment.Start,
    modifier = modifier
  ) {
    Content(
      pickerState = rememberFilePickerState(initAction = initMode),
      fileType = PickerType.Note,
      files = files,
    )
  }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun ColumnScope.Content(
  pickerState: PickerState,
  fileType: PickerType,
  files: State<ImmutableList<FileDataUI>>?,
  modifierEmptyList: Modifier = Modifier,
  modifierFilePicker: Modifier = Modifier,
  sharedTransitionScope: SharedTransitionScope? = null,
  animatedVisibilityScope: AnimatedVisibilityScope? = null,
  action: (HiddenFilesComponent.Action) -> Unit = {},
) {
  when (files?.value?.isNotEmpty()) {
    true -> {
      val mode by remember { pickerState.action }

      FilePicker(
        state = pickerState,
        files = files,
        contentPadding = PaddingValues(top = 4.dp, bottom = 40.dp),
        onClick = { action(HiddenFilesComponent.Action.OpenFilesOpener(it)) },
        onSelect = {
          if (it.isEmpty()) {
            pickerState.setAction(PickerAction.Click)
          }

          action(HiddenFilesComponent.Action.Selected(it))
        },
        sharedTransitionScope = sharedTransitionScope,
        animatedVisibilityScope = animatedVisibilityScope,
        modifier = modifierFilePicker
          .weight(weight = 1f)
          .padding(
            horizontal = when (pickerState.mode.value) {
              PickerMode.Gallery -> 16.dp
              PickerMode.Folder -> 0.dp
            },
          )
      )

      when (mode) {
        PickerAction.Click -> AddButton(fileType = fileType, action = action)
        PickerAction.Select -> StateButton(fileType = fileType, action = action)
      }
    }

    else -> EmptyList(
      modifier = modifierEmptyList,
      fileType = fileType,
      action = action,
    ).also {
      pickerState.setAction(PickerAction.Click)
    }
  }
}

@Composable
private fun AddButton(
  fileType: PickerType,
  action: (HiddenFilesComponent.Action) -> Unit,
) {
  if (fileType != PickerType.Trash) {
    HorizontalDivider()

    ActionButton(
      iconStart = MagicIcons.All.Plus,
      text = stringResource(
        resource = when (fileType) {
          PickerType.Video -> Res.string.add_videos
          PickerType.Photo -> Res.string.add_photo
          PickerType.File -> Res.string.add_file
          PickerType.Note -> Res.string.add_new_notes
          else -> Res.string.empty
        }
      ),
      action = { action(HiddenFilesComponent.Action.Add) },
      iconStartSize = IconSize(size = 20.dp),
      colors = ButtonColors.default(
        containerColor = MaterialTheme.colorScheme.tertiary,
      ),
      modifier = Modifier
        .fillMaxWidth()
        .padding(all = 16.dp)
    )
  }
}

@Composable
private fun StateButton(
  fileType: PickerType,
  action: (HiddenFilesComponent.Action) -> Unit,
) {
  HorizontalDivider()

  Row(
    horizontalArrangement = Arrangement.Start,
    verticalAlignment = Alignment.Top,
    modifier = Modifier
      .padding(all = 16.dp)
  ) {
    if (fileType != PickerType.Note) {
      ActionButton(
        iconStart = when (fileType) {
          PickerType.Trash -> MagicIcons.All.Restore
          else -> MagicIcons.All.Visible
        },
        text = stringResource(
          resource = when (fileType) {
            PickerType.Trash -> Res.string.restore
            else -> Res.string.visible
          },
        ),
        action = {
          action(
            when (fileType) {
              PickerType.Trash -> HiddenFilesComponent.Action.Restore
              else -> HiddenFilesComponent.Action.Visible
            }
          )
        },
        colors = ButtonColors.default(
          containerColor = MaterialTheme.colorScheme.secondary,
        ),
        modifier = Modifier
          .weight(weight = 1f)
      )
    }

    Spacer(modifier = Modifier.width(width = 10.dp))

    ActionButton(
      iconStart = MagicIcons.All.Delete,
      text = stringResource(resource = Res.string.delete),
      action = { action(HiddenFilesComponent.Action.Delete) },
      colors = ButtonColors.default(
        containerColor = MaterialTheme.colorScheme.error,
      ),
      modifier = Modifier
        .weight(weight = 1f)
    )
  }
}