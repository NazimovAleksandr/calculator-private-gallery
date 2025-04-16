package com.next.level.solutions.calculator.fb.mp.ui.screen.browser.composable.browser

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import coil3.Bitmap
import com.multiplatform.webview.web.NativeWebView
import com.multiplatform.webview.web.PlatformWebViewParams
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.WebViewNavigator
import com.multiplatform.webview.web.WebViewState
import com.next.level.solutions.calculator.fb.mp.ui.screen.browser.composable.browser.actions.BrowserViewActions

@Composable
inline fun BrowserView(
  webViewState: WebViewState,
  navigator: WebViewNavigator,
  darkTheme: Boolean,
  modifier: Modifier = Modifier,
  crossinline actions: (BrowserViewActions) -> Unit,
) {
  val pageIconValue: MutableState<Bitmap?> = remember {
    mutableStateOf(null)
  }

  WebView(
    state = webViewState,
    navigator = navigator,
    captureBackPresses = false,
    modifier = modifier,
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

  DisposableEffect(key1 = Unit) {
    webViewState.webSettings.apply {
      androidWebSettings.apply {
        domStorageEnabled = true
        isAlgorithmicDarkeningAllowed = darkTheme
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