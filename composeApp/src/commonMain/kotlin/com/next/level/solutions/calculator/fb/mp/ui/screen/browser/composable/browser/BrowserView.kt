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

//@Composable
//fun rememberBrowserWebViewClient(
//  okHttp: OkHttpClient? = null,
//  urlData: UrlData? = null,
//  actions: (BrowserWebViewClientActions) -> Unit,
//): BrowserWebViewClient {
//  val context = LocalContext.current
//
//  val webFilter = WebFilter.instance()
//  val scriptManager = ScriptManager.instance(context)
//
//  val scope = rememberCoroutineScope()
//
//  fun onLoadResource(
//    view: WebView?,
//    first: String?,
//    second: String? = first,
//  ) {
//    first ?: return
//    second ?: return
//
//    if (first.isEmpty()) return
//
//    scope.launch(Dispatchers.Main) {
//      val title = view?.title ?: return@launch
//
//      actions.invoke(
//        BrowserWebViewClientActions.LoadResource(
//          first = first,
//          second = second,
//          title = title,
//          okHttp = okHttp,
//        )
//      )
//    }
//  }
//
//  fun doUpdateVisitedHistory(url: String) {
//    actions.invoke(BrowserWebViewClientActions.UpdateUrl(url = url))
//  }
//
//  fun onUrlLoading(url: String, isRedirect: Boolean) {
//    actions.invoke(BrowserWebViewClientActions.UrlLoading(url = url, redirect = isRedirect))
//  }
//
//  fun onPageCommitVisible(url: String?, title: String?) {
//    actions.invoke(BrowserWebViewClientActions.OnPageCommitVisible(url, title))
//  }
//
//  fun onPageStarted(url: String?, title: String?) {
//    actions.invoke(BrowserWebViewClientActions.OnPageStarted(url, title))
//  }
//
//  fun onPageFinished(url: String?, title: String?) {
////    activity.lifecycleScope.launch(Dispatchers.IO) {
////      CookieManager.getInstance().flush()
////    }
//
//    actions.invoke(BrowserWebViewClientActions.OnPageFinished(url, title))
//  }
//
//  fun onErrorConnection() {
//    actions.invoke(BrowserWebViewClientActions.OnErrorConnection)
//  }
//
//  return remember {
//    BrowserWebViewClient(
//      doUpdateVisitedHistory = ::doUpdateVisitedHistory,
//      onUrlLoading = ::onUrlLoading,
//      onLoadResource = ::onLoadResource,
//      onPageCommitVisible = ::onPageCommitVisible,
//      onPageStarted = ::onPageStarted,
//      onPageFinished = ::onPageFinished,
//      onErrorConnection = ::onErrorConnection,
//    )
//  }
//}

//@Composable
//fun rememberBrowserWebChromeClient(
//  actions: (BrowserWebChromeClientActions) -> Unit,
//): BrowserWebChromeClient {
//
//  fun showCustomView(view: View?) {
//    actions.invoke(BrowserWebChromeClientActions.ShowCustomView(view = view))
//  }
//
//  fun hideCustomView() {
//    actions.invoke(BrowserWebChromeClientActions.HideCustomView)
//  }
//
//  fun createWindow(view: WebView, url: String) {
//    actions.invoke(BrowserWebChromeClientActions.CreateWindow(view = view, url = url))
//  }
//
//  fun receivedIcon(icon: Bitmap?) {
//    actions.invoke(BrowserWebChromeClientActions.ReceivedIcon(icon = icon))
//  }
//
//  fun receivedTitle(title: String?) {
//    actions.invoke(BrowserWebChromeClientActions.ReceivedTitle(title = title))
//  }
//
//  return remember {
//    BrowserWebChromeClient(
//      showCustomView = ::showCustomView,
//      hideCustomView = ::hideCustomView,
//      createWindow = ::createWindow,
//      receivedIcon = ::receivedIcon,
//      receivedTitle = ::receivedTitle,
//    )
//  }
//}

@Composable
fun BrowserView(
  webViewState: WebViewState,
  navigator: WebViewNavigator,
//  webViewClient: BrowserWebViewClient,
//  webChromeClient: BrowserWebChromeClient,
  modifier: Modifier = Modifier,
//  multipleWindows: Boolean = false,
  actions: (BrowserViewActions) -> Unit,
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

  val platformContext = LocalPlatformContext.current

  val pageIconRequest = remember(key1 = pageIconValue.value) {
    ImageRequest.Builder(platformContext)
      .data(pageIconValue.value)
      .memoryCacheKey(cacheKey)
      .diskCacheKey(cacheKey)
      .diskCachePolicy(CachePolicy.ENABLED)
      .memoryCachePolicy(CachePolicy.ENABLED)
      .build()
  }

  Box(
    modifier = modifier
  ) {
    AsyncImage(
      model = pageIconRequest,
      contentDescription = null,
      modifier = Modifier
        .size(size = 50.dp)
        .padding(all = 10.dp)
    )

    WebView(
      state = webViewState,
      navigator = navigator,
      captureBackPresses = false,
//    onCreated = { it: NativeWebView ->
//      it.initWebView(
//        multipleWindows = multipleWindows,
//        scope = scope,
//      )
//    },

//    client = webViewClient,
//    chromeClient = webChromeClient,
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
      modifier = Modifier.fillMaxSize()
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