package com.next.level.solutions.calculator.fb.mp.ui.screen.file.hider

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import calculator_fileblocking.composeapp.generated.resources.Res
import calculator_fileblocking.composeapp.generated.resources.hide
import calculator_fileblocking.composeapp.generated.resources.no_files_selected
import calculator_fileblocking.composeapp.generated.resources.no_photos_selected
import calculator_fileblocking.composeapp.generated.resources.no_videos_selected
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.nativ.NativeSize
import com.next.level.solutions.calculator.fb.mp.entity.ui.FileDataUI
import com.next.level.solutions.calculator.fb.mp.ui.composable.action.button.ActionButton
import com.next.level.solutions.calculator.fb.mp.ui.composable.check.box.CheckBox
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.FilePicker
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.PickerType
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.PickerAction
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.PickerMode
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.rememberFilePickerState
import com.next.level.solutions.calculator.fb.mp.ui.composable.image.Image
import com.next.level.solutions.calculator.fb.mp.ui.composable.toolbar.Toolbar
import com.next.level.solutions.calculator.fb.mp.ui.screen.file.hider.composable.Spinner
import com.next.level.solutions.calculator.fb.mp.ui.theme.TextStyleFactory
import com.next.level.solutions.calculator.fb.mp.utils.withNotNull
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.stringResource

@Composable
fun FileHiderContent(
  component: FileHiderComponent,
  modifier: Modifier = Modifier,
) {
  Content(
    component = component,
    modifier = modifier,
  )
}

@Composable
fun FileHiderContentPreview(
  modifier: Modifier = Modifier,
) {
  Content(
    component = null,
    modifier = modifier,
  )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun Content(
  component: FileHiderComponent?,
  modifier: Modifier = Modifier,
) {
  val model = component?.model?.subscribeAsState()

  val selectedItemCount by remember {
    derivedStateOf {
      model?.value?.selectedItemCount ?: 0
    }
  }

  val hideButtonEnabled by remember {
    derivedStateOf {
      (model?.value?.selectedItemCount ?: 0) > 0
    }
  }

  val fileType by remember {
    derivedStateOf {
      model?.value?.fileType
    }
  }

  val dark = isSystemInDarkTheme()

  val viewType: PickerMode by remember {
    derivedStateOf {
      model?.value?.viewType ?: when (dark) {
        true -> PickerMode.Gallery
        else -> PickerMode.Folder
      }
    }
  }

  val files = remember {
    derivedStateOf {
      model?.value?.files ?: persistentListOf()
    }
  }

  val folders: ImmutableList<FileDataUI> by remember {
    derivedStateOf {
      model?.value?.folders ?: persistentListOf()
    }
  }

  val selectedFolder by remember {
    derivedStateOf {
      model?.value?.selectedFolder
    }
  }

  val currentFolder by remember {
    derivedStateOf {
      model?.value?.currentFolder
    }
  }

  val allChecked by remember {
    derivedStateOf {
      selectedItemCount == files.value.size
    }
  }

  val viewTypeGallery by remember {
    derivedStateOf {
      viewType == PickerMode.Gallery
    }
  }

  val filePickerState = rememberFilePickerState(
    initAction = PickerAction.Select,
    initMode = viewType,
  )

  Column(
    verticalArrangement = Arrangement.Top,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier
      .background(color = MaterialTheme.colorScheme.background)
      .navigationBarsPadding()
      .statusBarsPadding()
  ) {
    Toolbar(
      title = {
        when (viewType) {
          PickerMode.Gallery -> Spinner(
            items = folders,
            selected = selectedFolder,
            onSelect = { folder ->
              folder?.let {
                component?.action(FileHiderComponent.Action.SelectFolder(folder = folder))
              }
            },
            modifier = Modifier
          )

          PickerMode.Folder -> Text(
            text = currentFolder?.name.toString(),
            style = TextStyleFactory.FS22.w400(),
            modifier = Modifier
          )
        }

        Spacer(modifier = Modifier.weight(weight = 1f))
      },
      navAction = { component?.action(FileHiderComponent.Action.Back) },
      menu = {
        if (viewTypeGallery) {
          CheckBox(
            checked = allChecked,
            onClick = {
              when (allChecked) {
                true -> filePickerState.allSelect(false)
                else -> filePickerState.allSelect(true)
              }
            },
            modifier = Modifier
              .clip(shape = MaterialTheme.shapes.small)
              .graphicsLayer(alpha = if (allChecked) 1f else 0.4f)
          )
        }
      }
    )

    if (viewType == PickerMode.Folder) {
      val scrollState = rememberScrollState()

      Row(
        horizontalArrangement = Arrangement.spacedBy(space = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
          .align(alignment = Alignment.Start)
          .horizontalScroll(state = scrollState)
          .padding(start = 16.dp, end = 40.dp)
      ) {
        folders.forEach { folder ->
          Row(
            horizontalArrangement = Arrangement.spacedBy(space = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
              .clip(shape = CircleShape)
              .clickable { component?.action(FileHiderComponent.Action.SelectFolder(folder = folder)) }
              .padding(vertical = 8.dp)
          ) {
            Text(
              text = folder.name,
              style = TextStyleFactory.FS12.w400(),
              modifier = Modifier
                .alpha(alpha = 0.9f)
            )

            Image(
              vector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
              colorFilter = MaterialTheme.colorScheme.primary,
              modifier = Modifier
                .size(size = 16.dp)
            )
          }
        }
      }

      val scrollValue = with(LocalDensity.current) { 16.dp.toPx() }

      LaunchedEffect(key1 = folders.size) {
        if (folders.isNotEmpty()) {
          scrollState.animateScrollBy(
            value = folders.last().name.length * 15f + scrollValue,
            animationSpec = tween(
              durationMillis = 1000,
              easing = CubicBezierEasing(0.42f, 0.0f, 0.42f, 1.0f),
            ),
          )
        }
      }
    }

    FilePicker(
      state = filePickerState,
      files = files,
      contentPadding = PaddingValues(top = 4.dp, bottom = 40.dp),
      onClick = {},
      onSelect = { component?.action(FileHiderComponent.Action.Selected(it)) },
      modifier = Modifier
        .weight(weight = 1f)
        .padding(
          horizontal = when (viewType) {
            PickerMode.Gallery -> 16.dp
            PickerMode.Folder -> 0.dp
          }
        )
    )

    HorizontalDivider()

    ActionButton(
      text = when {
        selectedItemCount == 0 -> {
          stringResource(
            resource = when (fileType) {
              PickerType.Photo -> Res.string.no_photos_selected
              PickerType.Video -> Res.string.no_videos_selected
              PickerType.File -> Res.string.no_files_selected
              else -> Res.string.no_photos_selected
            }
          )
        }

        else -> stringResource(resource = Res.string.hide, selectedItemCount)
      },
      enabled = hideButtonEnabled,
      action = { component?.action(FileHiderComponent.Action.Hide) },
      modifier = Modifier
        .fillMaxWidth()
        .padding(all = 16.dp)
    )

    withNotNull(component) {
      nativeAdCard(
        size = NativeSize.Small,
      )
    }
  }
}