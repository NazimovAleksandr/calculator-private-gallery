package com.next.level.solutions.calculator.fb.mp.ui.screen.hidden.files.conposable

import VideoPlayer
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import coil3.toUri
import com.next.level.solutions.calculator.fb.mp.entity.ui.FileDataUI
import com.next.level.solutions.calculator.fb.mp.expect.systemBars
import com.next.level.solutions.calculator.fb.mp.extensions.composable.sharedElementExt
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.FilePickerFileType
import com.next.level.solutions.calculator.fb.mp.ui.composable.image.Image
import com.next.level.solutions.calculator.fb.mp.ui.composable.toolbar.Toolbar
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Delete
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Videos
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Visible
import com.next.level.solutions.calculator.fb.mp.ui.screen.hidden.files.HiddenFilesComponent
import com.next.level.solutions.calculator.fb.mp.ui.theme.TextStyleFactory
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun FilesOpener(
  fileType: FilePickerFileType,
  firstFile: FileDataUI,
  files: ImmutableList<FileDataUI>?,
  modifier: Modifier = Modifier,
  sharedTransitionScope: SharedTransitionScope?,
  animatedVisibilityScope: AnimatedVisibilityScope?,
  action: (HiddenFilesComponent.Action) -> Unit = {},
) {
  val filesValue = remember(files) {
    files ?: persistentListOf()
  }

  Content(
    fileType = fileType,
    firstFile = firstFile,
    files = filesValue,
    modifier = modifier,
    sharedTransitionScope = sharedTransitionScope,
    animatedVisibilityScope = animatedVisibilityScope,
    action = action,
  )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun Content(
  fileType: FilePickerFileType,
  firstFile: FileDataUI,
  files: ImmutableList<FileDataUI>,
  modifier: Modifier = Modifier,
  sharedTransitionScope: SharedTransitionScope? = null,
  animatedVisibilityScope: AnimatedVisibilityScope? = null,
  action: (HiddenFilesComponent.Action) -> Unit = {},
) {
//  val density = LocalDensity.current

//  val scope = rememberCoroutineScope()

  val fileData = remember(firstFile) {
    mutableStateOf(firstFile)
  }

  val fullScreenState = remember {
    mutableStateOf(false)
  }

  val landscapeState: MutableState<Boolean> = remember {
    mutableStateOf(false)
  }

  val alphaAnimationState = remember {
    mutableStateOf(false)
  }

  val pagerState = rememberPagerState(
    initialPage = files.indexOfFirst { it.hiddenPath == firstFile.hiddenPath },
    pageCount = { files.size }
  )

//  val exoPlayer: ExoPlayer = remember {
//    ExoPlayer.Builder(context).build()
//      .apply {
//        try {
//          playWhenReady = true
//
//          addListener(object : Player.Listener {
////            override fun onPlayerError(error: PlaybackException) {
////              super.onPlayerError(error)
////              marketing.analytics.player.error()
////            }
////
////            override fun onIsPlayingChanged(isPlaying: Boolean) {
////              super.onIsPlayingChanged(isPlaying)
////
////              when (isPlaying) {
////                false -> marketing.analytics.player.close(time = System.currentTimeMillis() - startTime)
////                true -> startTime = System.currentTimeMillis()
////              }
////            }
//          })
//
////          marketing.analytics.player.launch()
//
//        } catch (ignore: Exception) {
//        }
//      }
//  }

//  fun onDispose() {
//    exoPlayer.release()
//    activity.onDisposeScreen()
//  }

  fun onLandscapeChange(state: Boolean) {
    landscapeState.value = state
  }

  fun onFullScreenChange(state: Boolean) {
    fullScreenState.value = state
  }

  Box(
    contentAlignment = Alignment.TopStart,
    modifier = modifier
      .background(color = MaterialTheme.colorScheme.background)
      .fillMaxSize()
  ) {
    if (files.size > 0) {
      HorizontalPager(
        state = pagerState,
        pageContent = { page ->
          val file: FileDataUI = files.getOrNull(page) ?: return@HorizontalPager

          var playerVisible by remember {
            mutableStateOf(false)
          }

          Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
              .sharedElementExt(
                key = file.hiddenPath ?: file.path,
                sharedTransitionScope = sharedTransitionScope,
                animatedVisibilityScope = animatedVisibilityScope,
              )
          ) {
            FilePreview(
              file = file,
              modifier = Modifier
                .fillMaxSize()
                .pointerInput(key1 = Unit) {
                  detectTapGestures {
                    systemBars(show = fullScreenState.value)
                    fullScreenState.value = !fullScreenState.value
                  }
                }
            )

            if (file.fileType == FilePickerFileType.Video) {
              Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                  .fillMaxSize()
                  .pointerInput(key1 = Unit) {}
                  .background(color = Color.Black.copy(alpha = 0.6f))
              ) {
                if (!playerVisible) {
                  PlayButton { playerVisible = true }
                } else {
                  CircularProgressIndicator()
                }
              }
            }

            if (playerVisible) {
              Player(
                file = file,
                modifier = Modifier
                  .fillMaxSize()
                  .pointerInput(key1 = Unit) {}
              )
            }
          }
        },
      )
    }

    ScreenToolbar(
      fullScreenState = fullScreenState,
      alphaAnimationState = alphaAnimationState,
      fileData = fileData,
      fileType = fileType,
      action = action,
    )
  }

  LaunchedEffect(key1 = files) {
    if (files.isEmpty()) action(HiddenFilesComponent.Action.CloseFilesOpener)
  }

  LaunchedEffect(key1 = pagerState.currentPage) {
    if (files.isNotEmpty()) {
      fileData.value = files[pagerState.currentPage]
    }
  }

  LaunchedEffect(key1 = Unit) {
    alphaAnimationState.value = true
    systemBars(show = true)
  }

//  LifecycleEventListener(onDispose = ::onDispose)
}

@Composable
private fun ScreenToolbar(
  fullScreenState: State<Boolean>,
  alphaAnimationState: State<Boolean>,
  fileData: State<FileDataUI>,
  fileType: FilePickerFileType,
  action: (HiddenFilesComponent.Action) -> Unit,
) {
  val alphaAnimationStateValue by remember {
    derivedStateOf { alphaAnimationState.value }
  }

  val fullScreenStateValue by remember {
    derivedStateOf { fullScreenState.value }
  }

  val fileDataValue by remember {
    derivedStateOf { fileData.value }
  }

  val alphaAnimation by animateFloatAsState(
    targetValue = if (alphaAnimationStateValue) 1f else 0f,
    animationSpec = tween(
      durationMillis = 1500,
    )
  )

  val toolbarTranslationY by animateIntOffsetAsState(
    targetValue = if (!fullScreenStateValue) IntOffset.Zero else IntOffset(x = 0, y = -300),
    animationSpec = tween(
      durationMillis = 700,
    )
  )

  val statusBars = WindowInsets.statusBars
  val density = LocalDensity.current

  val statusBarsPadding by remember {
    mutableIntStateOf(statusBars.getTop(density))
  }

  fun actionVisible() {
    action(
      when (fileType) {
        FilePickerFileType.Trash -> HiddenFilesComponent.Action.RestoreFile(fileDataValue)
        else -> HiddenFilesComponent.Action.VisibleFile(fileDataValue)
      }
    )
  }

  fun actionDelete() {
    action(HiddenFilesComponent.Action.DeleteFile(fileDataValue))
  }

  Toolbar(
    title = {
      Text(
        text = fileDataValue.name,
        style = TextStyleFactory.FS22.w400(),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier
          .weight(weight = 1f)
      )
    },
    navAction = { action(HiddenFilesComponent.Action.CloseFilesOpener) },
    menu = {
      MenuItem(
        vector = MagicIcons.All.Visible,
        action = ::actionVisible,
      )

      MenuItem(
        vector = MagicIcons.All.Delete,
        action = ::actionDelete,
      )
    },
    modifier = Modifier
      .offset { toolbarTranslationY }
      .graphicsLayer { alpha = alphaAnimation }
      .background(color = Color.Black.copy(alpha = 0.4f))
      .padding(top = with(density) { statusBarsPadding.toDp() })
  )
}

@Composable
private fun MenuItem(
  vector: ImageVector,
  action: () -> Unit,
) {
  Image(
    vector = vector,
    colorFilter = MaterialTheme.colorScheme.onBackground,
    modifier = Modifier
      .clip(shape = MaterialTheme.shapes.small)
      .clickable(onClick = action)
      .padding(all = 8.dp)
  )
}

@Composable
private fun PlayButton(
  action: () -> Unit,
) {
  Image(
    vector = MagicIcons.All.Videos,
    modifier = Modifier
      .width(width = 80.dp)
      .height(height = 70.dp)
      .clickable(onClick = action)
  )
}

@Composable
private fun FilePreview(
  file: FileDataUI,
  modifier: Modifier = Modifier,
) {
  val painter = rememberAsyncImagePainter(
    model = file.hiddenPath?.toUri() ?: file.path.toUri(),
  )

  Image(
    painter = painter,
    contentDescription = null,
    modifier = modifier
  )
}

@Composable
private fun Player(
  file: FileDataUI,
  modifier: Modifier = Modifier,
) {
//  fun PlayerView.onStop() {
//    activity.screenOrientationPortrait()
//    setFullscreenButtonState(false)
//  }

  VideoPlayer(
    url = file.hiddenPath ?: file.path,
    autoPlay = true,
    showControls = true,
    modifier = modifier
//      .graphicsLayer(alpha = if (alphaValue) 1f else 0f)
  )
}

//private fun ComponentActivity.onDisposeScreen() {
//  systemBarsState(show = true)
//  screenOrientationState(landscape = false)
//}