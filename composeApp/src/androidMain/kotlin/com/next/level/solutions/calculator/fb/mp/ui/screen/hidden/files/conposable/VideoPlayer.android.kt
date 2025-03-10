package com.next.level.solutions.calculator.fb.mp.ui.screen.hidden.files.conposable

import android.view.View
import androidx.annotation.OptIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem.fromUri
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.next.level.solutions.calculator.fb.mp.entity.ui.FileDataUI
import com.next.level.solutions.calculator.fb.mp.expect.PlatformExp
import com.next.level.solutions.calculator.fb.mp.ui.composable.lifecycle.event.listener.LifecycleEventListener

@OptIn(UnstableApi::class)
@Composable
actual fun VideoPlayer(
  file: FileDataUI,
  modifier: Modifier,
  action: (VideoPlayerAction) -> Unit,
) {
  val context = LocalContext.current

  var alphaValue by remember {
    mutableStateOf(false)
  }

  var landscapeState by remember {
    mutableStateOf(false)
  }

  val playerView: PlayerView by remember {
    mutableStateOf(PlayerView(context))
  }

  val exoPlayer: ExoPlayer = remember {
    ExoPlayer.Builder(context).build()
      .apply {
        playWhenReady = true

        addListener(
          object : Player.Listener {
//              override fun onPlayerError(error: PlaybackException) {}
//              override fun onIsPlayingChanged(isPlaying: Boolean) {}
          }
        )
      }
  }

  fun PlayerView.onStop() {
    PlatformExp.screenOrientation(landscape = false)
    setFullscreenButtonState(false)
  }

  AndroidView(
    factory = {
      playerView.apply {
        resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
        player = exoPlayer

        setShowNextButton(false)
        setShowPreviousButton(false)

        setControllerVisibilityListener(
          PlayerView.ControllerVisibilityListener {
            val state = when (it) {
              View.VISIBLE -> false
              else -> true
            }

            if (!landscapeState) {
              PlatformExp.systemBars(show = !state)
            }

            action(VideoPlayerAction.FullScreenChange(state))
          }
        )

        setFullscreenButtonClickListener {
          action(VideoPlayerAction.LandscapeChange(it))

          PlatformExp.screenOrientation(landscape = it)
          PlatformExp.systemBars(show = !it)
        }

        fun getCurrentPlayerPosition() {
          when {
            exoPlayer.currentPosition > 200 -> {
              alphaValue = true
              action(VideoPlayerAction.FullScreenChange(true))
              PlatformExp.systemBars(show = false)
            }

            exoPlayer.isPlaying -> {
              playerView.postDelayed(::getCurrentPlayerPosition, 50)
            }
          }
        }

        exoPlayer.addListener(object : Player.Listener {
          override fun onIsPlayingChanged(isPlaying: Boolean) {
            super.onIsPlayingChanged(isPlaying)

            if (isPlaying) {
              playerView.postDelayed(::getCurrentPlayerPosition, 210)
            }
          }
        })
      }
    },
    modifier = modifier
      .graphicsLayer(alpha = if (alphaValue) 1f else 0f)
  )

  LaunchedEffect(key1 = Unit) {
    exoPlayer.setMediaItem(fromUri(file.hiddenPath ?: file.path))
  }

  LifecycleEventListener(
    onCreate = exoPlayer::prepare,
    onPause = exoPlayer::stop,
    onStop = playerView::onStop,
  )

  DisposableEffect(key1 = Unit) {
    onDispose {
      exoPlayer.release()
    }
  }
}