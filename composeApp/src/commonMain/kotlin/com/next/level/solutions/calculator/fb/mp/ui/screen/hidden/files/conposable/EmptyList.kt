package com.magiccalculatorlock.ui.screens.hidden.files.conposable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import calculator_fileblocking.composeapp.generated.resources.Res
import calculator_fileblocking.composeapp.generated.resources.add_file
import calculator_fileblocking.composeapp.generated.resources.add_new_notes
import calculator_fileblocking.composeapp.generated.resources.add_photo
import calculator_fileblocking.composeapp.generated.resources.add_videos
import calculator_fileblocking.composeapp.generated.resources.empty
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.FilePickerFileType
import com.next.level.solutions.calculator.fb.mp.ui.composable.image.Image
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Plus
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Tick
import com.next.level.solutions.calculator.fb.mp.ui.screen.hidden.files.HiddenFilesComponent
import com.next.level.solutions.calculator.fb.mp.ui.theme.TextStyleFactory
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ColumnScope.EmptyList(
  modifier: Modifier = Modifier,
  fileType: FilePickerFileType,
  action: (HiddenFilesComponent.Action) -> Unit,
) {
  Content(
    modifier = modifier,
    fileType = fileType,
    action = action,
  )
}

@Composable
fun EmptyListPreview(
  fileType: FilePickerFileType,
  modifier: Modifier = Modifier,
) {
  Column(
    verticalArrangement = Arrangement.Top,
    horizontalAlignment = Alignment.Start,
    modifier = modifier
  ) {
    Content(
      fileType = fileType,
    )
  }
}

@Composable
private fun ColumnScope.Content(
  modifier: Modifier = Modifier,
  fileType: FilePickerFileType,
  action: (HiddenFilesComponent.Action) -> Unit = {},
) {
  Column(
    verticalArrangement = Arrangement.spacedBy(space = 20.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier
      .weight(weight = 1f)
      .align(alignment = Alignment.CenterHorizontally)
  ) {
    Spacer(modifier = Modifier.weight(weight = 1f))

    Image(
      vector = when (fileType) {
        FilePickerFileType.Trash -> MagicIcons.All.Tick
        else -> MagicIcons.All.Plus
      },
      modifier = Modifier
        .clip(shape = CircleShape)
        .background(color = Color(0xFF44ACAC))
        .clickable(
          enabled = fileType != FilePickerFileType.Trash,
          onClick = { action(HiddenFilesComponent.Action.Add) },
        )
        .padding(all = 22.dp)
    )

    Text(
      text = stringResource(
        resource = when (fileType) {
          FilePickerFileType.Trash -> Res.string.empty
          FilePickerFileType.Video -> Res.string.add_videos
          FilePickerFileType.Photo -> Res.string.add_photo
          FilePickerFileType.File -> Res.string.add_file
          FilePickerFileType.Note -> Res.string.add_new_notes
        }
      ),
      style = TextStyleFactory.FS20.w600(),
      modifier = Modifier
    )

    Spacer(modifier = Modifier.weight(weight = 1f))
  }
}