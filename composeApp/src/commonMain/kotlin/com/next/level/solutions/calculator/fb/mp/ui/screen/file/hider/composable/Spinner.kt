package com.next.level.solutions.calculator.fb.mp.ui.screen.file.hider.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import calculator_privategallery.composeapp.generated.resources.Res
import calculator_privategallery.composeapp.generated.resources.all_images
import calculator_privategallery.composeapp.generated.resources.all_videos
import com.next.level.solutions.calculator.fb.mp.constants.ALL_FILES
import com.next.level.solutions.calculator.fb.mp.entity.ui.FileDataUI
import com.next.level.solutions.calculator.fb.mp.entity.ui.ParentFolderModelUI
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.PickerType
import com.next.level.solutions.calculator.fb.mp.ui.composable.image.Image
import com.next.level.solutions.calculator.fb.mp.ui.composable.lifecycle.event.listener.LifecycleEventListener
import com.next.level.solutions.calculator.fb.mp.ui.theme.TextStyleFactory
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun Spinner(
  items: ImmutableList<FileDataUI>,
  selected: FileDataUI?,
  modifier: Modifier = Modifier,
  onSelect: (item: FileDataUI?) -> Unit,
) {
  Content(
    items = items,
    selected = selected,
    modifier = modifier,
    onSelect = onSelect,
  )
}

@Composable
fun SpinnerPreview(
  modifier: Modifier = Modifier,
) {
  Content(
    items = persistentListOf(
      ParentFolderModelUI("", "All Files"),
      ParentFolderModelUI("", "Camera"),
    ),
    selected = ParentFolderModelUI("", "All Files"),
    modifier = modifier,
  )
}

@Composable
private fun Content(
  items: ImmutableList<FileDataUI>,
  selected: FileDataUI?,
  modifier: Modifier = Modifier,
  onSelect: (item: FileDataUI?) -> Unit = {},
) {
  var expanded by remember { mutableStateOf(false) }
  var selectedItem by remember(key1 = selected) { mutableStateOf(selected) }

  Box(
    contentAlignment = Alignment.TopStart,
    modifier = modifier
  ) {
    Row(
      horizontalArrangement = Arrangement.Start,
      verticalAlignment = Alignment.Top,
      modifier = Modifier
        .clickable { expanded = true }
    ) {
      Text(
        text = when (selectedItem?.name) {
          ALL_FILES -> when (selectedItem?.fileType) {
            PickerType.Photo -> stringResource(Res.string.all_images)
            PickerType.Video -> stringResource(Res.string.all_videos)
            PickerType.File -> stringResource(Res.string.all_images)
            PickerType.Trash -> stringResource(Res.string.all_images)
            else -> stringResource(Res.string.all_images)
          }

          else -> selectedItem?.name.toString()
        },
        style = TextStyleFactory.FS22.w400(),
        modifier = Modifier
      )

      Image(
        vector = Icons.Filled.ArrowDropDown,
        colorFilter = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier
          .rotate(if (expanded) 180f else 0f)
          .alpha(alpha = 0.6f)
      )
    }

    DropdownMenu(
      expanded = expanded,
      offset = DpOffset(x = 24.dp, y = 8.dp),
      onDismissRequest = { expanded = false },
      containerColor = MaterialTheme.colorScheme.surface,
      content = {
        items.forEach { item ->
          DropdownMenuItem(
            text = {
              Text(
                text = when (item.name) {
                  ALL_FILES -> when (item.fileType) {
                    PickerType.Photo -> stringResource(Res.string.all_images)
                    PickerType.Video -> stringResource(Res.string.all_videos)
                    PickerType.File -> stringResource(Res.string.all_images)
                    PickerType.Trash -> stringResource(Res.string.all_images)
                    PickerType.Note -> stringResource(Res.string.all_images)
                  }

                  else -> item.name
                },
                style = TextStyleFactory.FS16.w400(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
              )
            },
            onClick = {
              selectedItem = item
              onSelect(selectedItem)
              expanded = false
            },
            modifier = Modifier
              .let {
                if (item == selectedItem) it.background(color = Color(0x14E6E0E9))
                else it
              }
          )
        }
      },
      modifier = Modifier
        .widthIn(min = 180.dp)
    )
  }

  LifecycleEventListener(
    onPause = { expanded = false }
  )
}