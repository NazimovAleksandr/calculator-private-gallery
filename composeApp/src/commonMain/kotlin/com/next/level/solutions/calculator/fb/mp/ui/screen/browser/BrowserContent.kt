package com.next.level.solutions.calculator.fb.mp.ui.screen.browser

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.multiplatform.webview.web.WebViewState
import com.multiplatform.webview.web.rememberWebViewNavigator
import com.multiplatform.webview.web.rememberWebViewState
import com.next.level.solutions.calculator.fb.mp.ui.composable.back.handler.BackHandler
import com.next.level.solutions.calculator.fb.mp.ui.screen.browser.BrowserComponent.Action
import com.next.level.solutions.calculator.fb.mp.ui.screen.browser.composable.PageLoadingBar
import com.next.level.solutions.calculator.fb.mp.ui.screen.browser.composable.browser.BrowserView
import com.next.level.solutions.calculator.fb.mp.ui.screen.browser.composable.browser.actions.BrowserViewActions
import com.next.level.solutions.calculator.fb.mp.ui.screen.browser.composable.toolbar.BrowserToolbar
import com.next.level.solutions.calculator.fb.mp.ui.screen.browser.composable.toolbar.actions.ToolbarActions
import com.next.level.solutions.calculator.fb.mp.ui.screen.browser.composable.toolbar.actions.ToolbarActions.AddTab
import com.next.level.solutions.calculator.fb.mp.ui.screen.browser.composable.toolbar.actions.ToolbarActions.History
import com.next.level.solutions.calculator.fb.mp.ui.screen.browser.composable.toolbar.actions.ToolbarActions.Home
import com.next.level.solutions.calculator.fb.mp.ui.screen.browser.composable.toolbar.actions.ToolbarActions.InputSearch
import com.next.level.solutions.calculator.fb.mp.ui.screen.browser.composable.toolbar.actions.ToolbarActions.InputSearchClear
import com.next.level.solutions.calculator.fb.mp.ui.screen.browser.composable.toolbar.actions.ToolbarActions.Load
import com.next.level.solutions.calculator.fb.mp.ui.screen.browser.composable.toolbar.actions.ToolbarActions.Menu
import com.next.level.solutions.calculator.fb.mp.ui.screen.browser.composable.toolbar.actions.ToolbarActions.Reload

@Composable
internal fun BrowserContent(
  component: BrowserComponent,
  modifier: Modifier = Modifier,
) {
  Content(
    component = component,
    modifier = modifier,
  )
}

@Composable
fun BrowserContentPreview(
  modifier: Modifier = Modifier
) {
  Content(
    component = null,
    modifier = modifier,
  )
}

@Composable
private fun Content(
  component: BrowserComponent?,
  modifier: Modifier = Modifier,
) {
  val model = component?.model?.subscribeAsState()

  val loadUrl by remember {
    derivedStateOf {
      model?.value?.browserViewState?.loadUrl
    }
  }

  val initUrl by remember {
    derivedStateOf {
      model?.value?.browserViewState?.initUrl ?: "about:blank"
    }
  }

  val toolbarState = remember {
    derivedStateOf {
      model?.value?.toolbarState
    }
  }

  var toolbarHeight by remember {
    mutableIntStateOf(0)
  }

  val webViewState: WebViewState = rememberWebViewState(
    url = initUrl,
  )

  val navigator = rememberWebViewNavigator()

//  fun browserWebViewClientActions(it: BrowserWebViewClientActions) {
//    when (it) {
//      is UpdateUrl -> component?.action(BrowserComponent.Action.UpdateUrl(url = it.url))
//      is UrlLoading -> component?.action(BrowserComponent.Action.UrlLoading(url = it.url, redirect = it.redirect))
//      is LoadResource -> {}
//
//      is OnPageStarted -> component?.action(BrowserComponent.Action.PageStarted(url = it.url, title = it.title))
//      is OnPageFinished -> component?.action(BrowserComponent.Action.PageFinished(url = it.url, title = it.title))
//      is OnPageCommitVisible -> component?.action(BrowserComponent.Action.PageCommitVisible(url = it.url, title = it.title))
//
//      is OnErrorConnection -> {}
//    }
//  }

//  val webViewClient = rememberBrowserWebViewClient(
//    okHttp = null,
//    urlData = null,
//    actions = ::browserWebViewClientActions,
//  )

//  fun browserWebChromeClientActions(it: BrowserWebChromeClientActions) {
//    when (it) {
//      is ReceivedIcon -> component?.action(BrowserComponent.Action.ReceivedIcon(icon = it.icon, context = context))
//      is ReceivedTitle -> component?.action(BrowserComponent.Action.ReceivedTitle(title = it.title))
//      is ShowCustomView -> component?.action(BrowserComponent.Action.ShowCustomView(view = it.view))
//      is HideCustomView -> component?.action(BrowserComponent.Action.HideCustomView)
//      is CreateWindow -> {}
//    }
//  }

//  val webChromeClient = rememberBrowserWebChromeClient(
//    actions = ::browserWebChromeClientActions,
//  )

//  fun browserViewActions(it: BrowserViewActions) {
//    when (it) {
//      is OnTouchStarted -> {}
//      is OnTouchFinished -> {}
//      is OnVerticalScroll -> {}
//      is OnHorizontalScroll -> {}
//
//      is VideoLoadStart -> {}
//      is VideoCanPlay -> {}
//      is VideoPlaying -> {}
//      is VideoPlay -> {}
//      is VideoPause -> {}
//
//      is FindVideos -> {}
//      is TwVideos -> {}
//      is Videos -> {}
//
//      is PageDomLoaded -> {}
//      is PageLoad -> {}
//    }
//  }

  val toolbarActions: (ToolbarActions) -> Unit = remember {
    {
      when (it) {
        is Home -> component?.action(Action.Back)
        is Load -> component?.action(Action.Load(url = it.url))
        is Reload -> navigator.reload()

        is AddTab -> component?.action(Action.AddTab)
        is History -> component?.action(Action.History)

        is InputSearchClear -> {}
        is InputSearch -> {}
        is Menu -> {}
      }
    }
  }

  Column(
    verticalArrangement = Arrangement.Top,
    horizontalAlignment = Alignment.Start,
    modifier = modifier
      .background(color = MaterialTheme.colorScheme.background)
      .statusBarsPadding()
      .navigationBarsPadding()
  ) {
    BrowserToolbar(
      state = toolbarState,
      webViewState = webViewState,
      actions = toolbarActions,
      modifier = Modifier
        .onGloballyPositioned { if (toolbarHeight == 0) toolbarHeight = it.size.height }
        .padding(vertical = 8.dp)
    )

    PageLoadingBar(
      webViewState = webViewState,
      modifier = Modifier
    )

    BrowserView(
      webViewState = webViewState,
      navigator = navigator,
      modifier = Modifier
        .weight(weight = 1f)
    ) {
      val action: Action = when (it) {
        is BrowserViewActions.OnPageStarted -> Action.PageStarted(url = it.url, title = it.title)
        is BrowserViewActions.OnPageFinished -> Action.PageFinished(url = it.url, title = it.title)
        is BrowserViewActions.OnPageCommitVisible -> Action.PageCommitVisible(url = it.url, title = it.title)

        is BrowserViewActions.OnReceivedIcon -> Action.ReceivedIcon(icon = it.icon)
        is BrowserViewActions.OnCreateWindow -> Action.CreateWindow(view = it.view, url = it.url)
      }

      component?.action(action)
    }
  }

  component?.backHandler?.let { backHandler ->
    BackHandler(
      backHandler = backHandler,
    ) {
      when (navigator.canGoBack) {
        true -> navigator.navigateBack()
        else -> component.action(Action.Back)
      }
    }
  }

  LaunchedEffect(key1 = loadUrl) {
    navigator.loadUrl(url = loadUrl ?: "about:blank")
  }
}