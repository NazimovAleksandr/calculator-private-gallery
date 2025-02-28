package com.next.level.solutions.calculator.fb.mp.ui.screen.browser.composable.browser

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.Bitmap
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import com.multiplatform.webview.web.NativeWebView
import com.multiplatform.webview.web.PlatformWebViewParams
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.WebViewNavigator
import com.multiplatform.webview.web.WebViewState
import com.next.level.solutions.calculator.fb.mp.ui.screen.browser.composable.browser.actions.BrowserViewActions
import io.ktor.http.Url

@Composable
inline fun BrowserView(
  webViewState: WebViewState,
  navigator: WebViewNavigator,
  modifier: Modifier = Modifier,
//  multipleWindows: Boolean = false todo ?,
  crossinline actions: (BrowserViewActions) -> Unit,
) {
  val pageIconValue: MutableState<Bitmap?> = remember {
    mutableStateOf(null)
  }

  val url by remember {
    derivedStateOf {
      webViewState.lastLoadedUrl
    }
  }

  val cacheKey = Url(url.toString()).host





  Box(
    modifier = modifier
  ) {


    WebView(
      state = webViewState,
      navigator = navigator,
      captureBackPresses = false,
      modifier = Modifier.fillMaxSize(),
      platformWebViewParams = getPlatformWebViewParams(
        onPageStarted = { url, title ->
          actions(BrowserViewActions.OnPageStarted(url, title))
        },
        onPageFinished = { url, title ->
          actions(BrowserViewActions.OnPageFinished(url, title))
        },
        onPageCommitVisible = { url, title ->
          actions(BrowserViewActions.OnPageCommitVisible(url, title))
        },
        onReceivedIcon = { icon ->
          pageIconValue.value = icon
          actions(BrowserViewActions.OnReceivedIcon(icon))
        },
        onCreateWindow = { view, url ->
          actions(BrowserViewActions.OnCreateWindow(view, url))
        }
      ),
    )
  }


  DisposableEffect(key1 = Unit) {
    webViewState.webSettings.apply {
      androidWebSettings.apply {
        domStorageEnabled = true
        isAlgorithmicDarkeningAllowed = true
      }
    }
    onDispose { }
  }
}

data class BrowserViewState(
  val initUrl: String = "",
  val loadUrl: String = "",
)

expect fun getPlatformWebViewParams(
  onPageStarted: (url: String?, title: String?) -> Unit,
  onPageFinished: (url: String?, title: String?) -> Unit,
  onPageCommitVisible: (url: String?, title: String?) -> Unit,
  onReceivedIcon: (icon: Bitmap?) -> Unit,
  onCreateWindow: (view: NativeWebView, url: String) -> Unit,
): PlatformWebViewParams