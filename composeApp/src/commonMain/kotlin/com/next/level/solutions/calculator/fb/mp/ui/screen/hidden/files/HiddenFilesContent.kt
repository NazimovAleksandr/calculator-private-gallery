package com.next.level.solutions.calculator.fb.mp.ui.screen.hidden.files

import androidx.compose.animation.AnimatedContent
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import calculator_fileblocking.composeapp.generated.resources.Res
import calculator_fileblocking.composeapp.generated.resources.date
import calculator_fileblocking.composeapp.generated.resources.file
import calculator_fileblocking.composeapp.generated.resources.name
import calculator_fileblocking.composeapp.generated.resources.photos
import calculator_fileblocking.composeapp.generated.resources.recycle_bin
import calculator_fileblocking.composeapp.generated.resources.secret_notes
import calculator_fileblocking.composeapp.generated.resources.size
import calculator_fileblocking.composeapp.generated.resources.sort_by
import calculator_fileblocking.composeapp.generated.resources.videos
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.nativ.NativeSize
import com.next.level.solutions.calculator.fb.mp.entity.ui.FileDataUI
import com.next.level.solutions.calculator.fb.mp.expect.PlatformExp
import com.next.level.solutions.calculator.fb.mp.ui.composable.back.handler.BackHandler
import com.next.level.solutions.calculator.fb.mp.ui.composable.check.box.CheckBox
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.PickerAction
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.PickerMode
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.PickerType
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.rememberFilePickerState
import com.next.level.solutions.calculator.fb.mp.ui.composable.lifecycle.event.listener.LifecycleEventListener
import com.next.level.solutions.calculator.fb.mp.ui.composable.magic.menu.MagicMenu
import com.next.level.solutions.calculator.fb.mp.ui.composable.magic.menu.MagicMenuItem
import com.next.level.solutions.calculator.fb.mp.ui.composable.toolbar.Toolbar
import com.next.level.solutions.calculator.fb.mp.ui.screen.hidden.files.conposable.Files
import com.next.level.solutions.calculator.fb.mp.ui.screen.hidden.files.conposable.FilesOpener
import com.next.level.solutions.calculator.fb.mp.ui.theme.TextStyleFactory
import com.next.level.solutions.calculator.fb.mp.utils.withNotNull
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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

  LifecycleEventListener(
    onResume = {
      if (
        component.model.value.fileType != PickerType.Note
        && !PlatformExp.externalStoragePermissionGranted()
      ) {
        component.action(HiddenFilesComponent.Action.Back)
      }
    },
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
  component: HiddenFilesComponent,
  modifier: Modifier = Modifier,
) {
  val model = component.model.subscribeAsState()

  val openFile: FileDataUI? by remember {
    derivedStateOf {
      model.value.openFile
    }
  }

  val navigationBars = WindowInsets.navigationBars
  val density = LocalDensity.current

  var landscapeState by remember {
    mutableStateOf(false)
  }

  val navigationBarsPadding by remember(key1 = landscapeState) {
    mutableIntStateOf(
      if (landscapeState) 0
      else navigationBars.getBottom(density)
    )
  }

  val filesState by remember {
    derivedStateOf {
      model.value.files
    }
  }

  val files: State<ImmutableList<FileDataUI>> = filesState.collectAsStateWithLifecycle()

  Column(
    modifier = modifier
      .background(color = MaterialTheme.colorScheme.background)
      .padding(bottom = with(density) { navigationBarsPadding.toDp() })
  ) {
    SharedTransitionLayout(
      modifier = Modifier
        .weight(weight = 1f)
    ) {
      AnimatedContent(
        targetState = openFile,
        label = "",
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

    if (!landscapeState) {
      withNotNull(component) {
        if (files.value.isNotEmpty()) {
          nativeAdCard(
            size = NativeSize.Small,
          )
        } else {
          nativeAdCard(
            size = NativeSize.Large,
          )
        }
      }
    }
  }

  DisposableEffect(key1 = Unit) {
    PlatformExp.setScreenOrientationListener { landscapeState = it }
    onDispose { PlatformExp.setScreenOrientationListener(null) }
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
  val scope = rememberCoroutineScope()

  val model = component?.model?.subscribeAsState()

  val selectedItemCount by remember {
    derivedStateOf {
      model?.value?.selectedItemCount ?: 0
    }
  }

  val fileType by remember {
    derivedStateOf {
      model?.value?.fileType ?: PickerType.File
    }
  }

  val viewType by remember {
    derivedStateOf {
      model?.value?.viewType ?: PickerMode.Gallery
    }
  }

  val filesState by remember {
    derivedStateOf {
      model?.value?.files
    }
  }

  val files = filesState?.collectAsStateWithLifecycle()

  val filePickerState = rememberFilePickerState(
    initAction = PickerAction.Click,
    initMode = viewType,
  )

  val mode by remember {
    filePickerState.action
  }

  val allChecked by remember {
    derivedStateOf {
      selectedItemCount == files?.value?.size
    }
  }

  val selectMode by remember {
    derivedStateOf {
      mode == PickerAction.Select
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

  val menuItems = remember(key1 = fileType) {
    mutableStateOf(
      if (fileType == PickerType.Note) {
        persistentListOf(
          MagicMenuItem(
            text = Res.string.date,
            action = { component?.action(HiddenFilesComponent.Action.SortByDateAdded) },
          ),
          MagicMenuItem(
            text = Res.string.name,
            action = { component?.action(HiddenFilesComponent.Action.SortByName) },
          ),
        )
      } else {
        persistentListOf(
          MagicMenuItem(
            text = Res.string.date,
            action = { component?.action(HiddenFilesComponent.Action.SortByDateAdded) },
          ),
          MagicMenuItem(
            text = Res.string.name,
            action = { component?.action(HiddenFilesComponent.Action.SortByName) },
          ),
          MagicMenuItem(
            text = Res.string.size,
            action = { component?.action(HiddenFilesComponent.Action.SortByFileSize) },
          ),
        )
      }
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
                PickerType.File -> Res.string.file
                PickerType.Photo -> Res.string.photos
                PickerType.Trash -> Res.string.recycle_bin
                PickerType.Video -> Res.string.videos
                PickerType.Note -> Res.string.secret_notes
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
          if (selectMode) {
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
            )
          } else {
            MagicMenu(
              items = menuItems,
              withSelected = true,
              modifier = Modifier
                .padding(top = 4.dp)
            ) {
              Text(
                text = stringResource(resource = Res.string.sort_by),
                style = TextStyleFactory.FS13.w600(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                  .padding(top = 2.dp, end = 6.dp)
              )
            }
          }
        }
      )

      Files(
        pickerState = filePickerState,
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
        onBack = { filePickerState.setAction(PickerAction.Click) },
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
          scope.launch {
            PlatformExp.systemBars(show = true)
            PlatformExp.screenOrientation(landscape = false)
            delay(200)
            component.action(HiddenFilesComponent.Action.CloseFilesOpener)
          }
        },
      )
    }
  }
}