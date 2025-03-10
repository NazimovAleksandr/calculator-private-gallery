package com.next.level.solutions.calculator.fb.mp.ui.screen.hidden.files.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import calculator_fileblocking.composeapp.generated.resources.Res
import calculator_fileblocking.composeapp.generated.resources.app_name
import calculator_fileblocking.composeapp.generated.resources.choose_images_from
import calculator_fileblocking.composeapp.generated.resources.choose_videos_from
import calculator_fileblocking.composeapp.generated.resources.folder
import calculator_fileblocking.composeapp.generated.resources.gallery
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.next.level.solutions.calculator.fb.mp.ui.composable.action.button.ActionButton
import com.next.level.solutions.calculator.fb.mp.ui.composable.action.button.ButtonColors
import com.next.level.solutions.calculator.fb.mp.ui.composable.action.button.IconSize
import com.next.level.solutions.calculator.fb.mp.ui.composable.action.button.IconType
import com.next.level.solutions.calculator.fb.mp.ui.composable.bottom.sheet.BottomSheet
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.PickerType
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Folder
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Photos
import com.next.level.solutions.calculator.fb.mp.ui.theme.TextStyleFactory
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseDialogContent(
  component: ChooseDialogComponent,
) {
  val model by component.model.subscribeAsState()

  val titleRes by remember {
    derivedStateOf {
      when (model.fileType) {
        PickerType.Photo -> Res.string.choose_images_from
        PickerType.Video -> Res.string.choose_videos_from
        else -> Res.string.app_name
      }
    }
  }

  fun hide() {
    component.action(ChooseDialogComponent.Action.Hide)
  }

  fun actionGallery() {
    component.action(ChooseDialogComponent.Action.Gallery)
  }

  fun actionFolder() {
    component.action(ChooseDialogComponent.Action.Folder)
  }

  BottomSheet(
    onDismissRequest = ::hide,
    content = {
      Content(
        title = stringResource(resource = titleRes),
        actionGallery = {
          actionGallery()
          it.dismiss()
        },
        actionFolder = {
          actionFolder()
          it.dismiss()
        },
      )
    },
  )
}

@Composable
fun ChooseDialogContentPreview(
  modifier: Modifier = Modifier,
) {
  Column(
    verticalArrangement = Arrangement.Top,
    horizontalAlignment = Alignment.Start,
    modifier = modifier
      .background(color = MaterialTheme.colorScheme.secondaryContainer)
  ) {
    Content()
  }
}

@Composable
private fun Content(
  title: String = "choose_images_from",
  actionGallery: () -> Unit = {},
  actionFolder: () -> Unit = {},
) {
  Text(
    text = title,
    style = TextStyleFactory.FS20.w600(),
    textAlign = TextAlign.Center,
    modifier = Modifier
      .fillMaxWidth()
  )

  Spacer(modifier = Modifier.height(height = 16.dp))

  Row(
    horizontalArrangement = Arrangement.spacedBy(space = 12.dp),
    verticalAlignment = Alignment.Top,
    modifier = Modifier
      .padding(horizontal = 16.dp)
      .padding(bottom = 16.dp)
  ) {
    ActionButton(
      iconTop = MagicIcons.All.Photos,
      text = stringResource(Res.string.gallery),
      iconTopSize = IconSize(0.dp),
      iconType = IconType.Image,
      colors = ButtonColors.default(
        containerColor = MaterialTheme.colorScheme.secondary,
      ),
      action = actionGallery,
      modifier = Modifier
        .weight(weight = 1f)
    )

    ActionButton(
      iconTop = MagicIcons.All.Folder,
      text = stringResource(Res.string.folder),
      iconTopSize = IconSize(0.dp),
      iconType = IconType.Image,
      colors = ButtonColors.default(
        containerColor = MaterialTheme.colorScheme.secondary,
      ),
      action = actionFolder,
      modifier = Modifier
        .weight(weight = 1f)
    )
  }
}