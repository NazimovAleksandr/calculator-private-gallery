package com.next.level.solutions.calculator.fb.mp.ui.screen.hidden.files

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import calculator_fileblocking.composeapp.generated.resources.Res
import calculator_fileblocking.composeapp.generated.resources.date_added
import calculator_fileblocking.composeapp.generated.resources.file
import calculator_fileblocking.composeapp.generated.resources.file_size
import calculator_fileblocking.composeapp.generated.resources.name
import calculator_fileblocking.composeapp.generated.resources.photos
import calculator_fileblocking.composeapp.generated.resources.recycle_bin
import calculator_fileblocking.composeapp.generated.resources.secret_notes
import calculator_fileblocking.composeapp.generated.resources.videos
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.nativ.NativeSize
import com.next.level.solutions.calculator.fb.mp.entity.ui.FileDataUI
import com.next.level.solutions.calculator.fb.mp.expect.systemBars
import com.next.level.solutions.calculator.fb.mp.ui.composable.back.handler.BackHandler
import com.next.level.solutions.calculator.fb.mp.ui.composable.check.box.CheckBox
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.FilePickerFileType
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.FilePickerMode
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.FilePickerViewType
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.rememberFilePickerState
import com.next.level.solutions.calculator.fb.mp.ui.composable.magic.menu.MagicMenu
import com.next.level.solutions.calculator.fb.mp.ui.composable.magic.menu.MagicMenuItem
import com.next.level.solutions.calculator.fb.mp.ui.composable.toolbar.Toolbar
import com.next.level.solutions.calculator.fb.mp.ui.screen.hidden.files.conposable.Files
import com.next.level.solutions.calculator.fb.mp.ui.screen.hidden.files.conposable.FilesOpener
import com.next.level.solutions.calculator.fb.mp.ui.theme.TextStyleFactory
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.stringResource

@Composable
fun HiddenFilesContent(
  component: HiddenFilesComponent,
  modifier: Modifier = Modifier,
) {
  AnimatedContent(
    component = component,
    modifier = modifier,
  )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HiddenFilesContentPreview(
  modifier: Modifier = Modifier,
) {
  Content(
    component = null,
    modifier = modifier,
  )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun AnimatedContent(
  component: HiddenFilesComponent?,
  modifier: Modifier = Modifier,
) {
  val model = component?.model?.subscribeAsState()

  val openFile: FileDataUI? by remember {
    derivedStateOf {
      model?.value?.openFile
    }
  }

  val navigationBars = WindowInsets.navigationBars
  val density = LocalDensity.current

  val navigationBarsPadding by remember {
    mutableIntStateOf(navigationBars.getBottom(density))
  }

  Column(
    modifier = modifier
      .background(color = MaterialTheme.colorScheme.background)
      .padding(bottom = with(density) { navigationBarsPadding.toDp() })
  ) {
    SharedTransitionLayout(
      modifier = Modifier
        .weight(weight = 1f)
    ) {
      androidx.compose.animation.AnimatedContent(
        targetState = openFile,
        label = "SharedTransition",
        modifier = Modifier
      ) {
        Content(
          component = component,
          openFile = it,
          modifier = Modifier,
          animatedVisibilityScope = this,
          sharedTransitionScope = this@SharedTransitionLayout,
        )
      }
    }

    HorizontalDivider()

    component?.nativeAdCard(
      size = NativeSize.Small,
    )

    HorizontalDivider()
  }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun Content(
  component: HiddenFilesComponent?,
  modifier: Modifier = Modifier,
  openFile: FileDataUI? = null,
  sharedTransitionScope: SharedTransitionScope? = null,
  animatedVisibilityScope: AnimatedVisibilityScope? = null,
) {
  val model = component?.model?.subscribeAsState()

  val selectedItemCount by remember {
    derivedStateOf {
      model?.value?.selectedItemCount ?: 0
    }
  }

  val fileType by remember {
    derivedStateOf {
      model?.value?.fileType ?: FilePickerFileType.File
    }
  }

  val viewType by remember {
    derivedStateOf {
      model?.value?.viewType ?: FilePickerViewType.Gallery
    }
  }

  val filesState by remember {
    derivedStateOf {
      model?.value?.files
    }
  }

  val filePickerState = rememberFilePickerState(
    initMode = FilePickerMode.Click,
    viewType = viewType,
  )

  val files = filesState?.collectAsStateWithLifecycle()

  val mode by remember {
    filePickerState.mode
  }

  val allChecked by remember {
    derivedStateOf {
      selectedItemCount == files?.value?.size
    }
  }

  val selectMode by remember {
    derivedStateOf {
      mode == FilePickerMode.Select
    }
  }

  val title = remember(
    key1 = mode,
    key2 = selectedItemCount,
  ) {
    when (selectMode) {
      true -> selectedItemCount.toString()
      else -> ""
    }
  }

  val menuItems = remember {
    mutableStateOf(
      persistentListOf(
        MagicMenuItem(
          text = Res.string.date_added,
          action = { component?.action(HiddenFilesComponent.Action.SortByDateAdded) },
        ),
        MagicMenuItem(
          text = Res.string.file_size,
          action = { component?.action(HiddenFilesComponent.Action.SortByFileSize) },
        ),
        MagicMenuItem(
          text = Res.string.name,
          action = { component?.action(HiddenFilesComponent.Action.SortByName) },
        ),
      )
    )
  }

  if (openFile == null) {
    Column(
      verticalArrangement = Arrangement.Top,
      horizontalAlignment = Alignment.Start,
      modifier = modifier
        .statusBarsPadding()
    ) {
      Toolbar(
        title = {
          Text(
            text = stringResource(
              resource = when (fileType) {
                FilePickerFileType.File -> Res.string.file
                FilePickerFileType.Photo -> Res.string.photos
                FilePickerFileType.Trash -> Res.string.recycle_bin
                FilePickerFileType.Video -> Res.string.videos
                FilePickerFileType.Note -> Res.string.secret_notes
              }
            ),
            style = TextStyleFactory.FS22.w400(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
          )

          Text(
            text = " $title",
            style = TextStyleFactory.FS22.w400(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
          )

          Spacer(modifier = Modifier.weight(weight = 1f))
        },
        navAction = {
          component?.action(HiddenFilesComponent.Action.Back)
        },
        menu = {
          CheckBox(
            checked = allChecked,
            visible = selectMode,
            onClick = {
              when (allChecked) {
                true -> filePickerState.allSelect(false)
                else -> filePickerState.allSelect(true)
              }
            },
            modifier = Modifier
              .clip(shape = MaterialTheme.shapes.small)
          )

          MagicMenu(
            items = menuItems,
          )
        }
      )

      Files(
        filePickerState = filePickerState,
        fileType = fileType,
        files = files,
        action = { component?.action(it) },
        modifierEmptyList = Modifier,
        modifierFilePicker = Modifier,
        animatedVisibilityScope = animatedVisibilityScope,
        sharedTransitionScope = sharedTransitionScope,
      )
    }

    component?.backHandler?.let { backHandler ->
      BackHandler(
        backHandler = backHandler,
        isEnabled = selectMode,
        onBack = { filePickerState.setMode(FilePickerMode.Click) },
      )
    }
  } else {
    FilesOpener(
      fileType = fileType,
      firstFile = openFile,
      files = files?.value,
      animatedVisibilityScope = animatedVisibilityScope,
      sharedTransitionScope = sharedTransitionScope,
      action = { component?.action(it) },
    )

    LaunchedEffect(key1 = Unit) {
      component?.action(HiddenFilesComponent.Action.LoadNative)
    }

    component?.backHandler?.let { backHandler ->
      BackHandler(
        backHandler = backHandler,
        onBack = {
          systemBars(show = true)
//          screenOrientationState(landscape = false) todo
//          delay(200)
          component.action(HiddenFilesComponent.Action.CloseFilesOpener)
        },
      )
    }
  }
}