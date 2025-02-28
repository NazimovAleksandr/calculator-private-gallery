package com.next.level.solutions.calculator.fb.mp.ui.screen.browser.composable.toolbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import coil3.Bitmap
import com.multiplatform.webview.web.WebViewState
import com.next.level.solutions.calculator.fb.mp.ui.screen.browser.composable.toolbar.actions.BrowserInputLinkActions
import com.next.level.solutions.calculator.fb.mp.ui.screen.browser.composable.toolbar.actions.ToolbarActions

@Composable
//@Suppress("NOTHING_TO_INLINE") // for fix recompositions
internal inline fun BrowserToolbar(
  state: State<BrowserToolbarState?>,
  webViewState: WebViewState,
  modifier: Modifier = Modifier,
  noinline actions: (ToolbarActions) -> Unit,
) {
  Content(
    state = state,
    webViewState = webViewState,
    modifier = modifier,
    actions = actions,
  )
}

@Composable
fun BrowserToolbarPreview() {
  Content(
    state = remember {
      mutableStateOf(
        BrowserToolbarState(
//          url = "http://www.example.com",
//          pageIcon = null,
//          title = "Title",
          toolbarOffset = 0f
        )
      )
    },
  )
}
@Composable
private fun Content(
  state: State<BrowserToolbarState?>,
  modifier: Modifier = Modifier,
  webViewState: WebViewState? = null,
  actions: (ToolbarActions) -> Unit = {},
) {
  var isEditLink by remember {
    mutableIntStateOf(0)
  }

  val url by remember {
    derivedStateOf {
      webViewState?.lastLoadedUrl
    }
  }

  val pageIcon = remember {
    derivedStateOf {
      state.value?.pageIcon
    }
  }

  val title = remember {
    derivedStateOf {
      webViewState?.pageTitle
    }
  }

  var linkEditorVisible by remember {
    mutableStateOf(state.value?.linkEditorVisible == true)
  }

  val keyboardController = LocalSoftwareKeyboardController.current
  val focusManager = LocalFocusManager.current

  Column(
    verticalArrangement = Arrangement.Top,
    horizontalAlignment = Alignment.Start,
    modifier = Modifier
      .background(color = MaterialTheme.colorScheme.background)
      .pointerInput(key1 = Unit) {}
  ) {
    Row(
      horizontalArrangement = Arrangement.Start,
      verticalAlignment = Alignment.CenterVertically,
      modifier = modifier
    ) {
      NavigationButton(
        linkEditor = linkEditorVisible,
        actions = actions,
      )

      BrowserInputLink(
        title = title.value,
        url = url,
        isEditLink = isEditLink,
        linkEditor = linkEditorVisible,
        modifier = Modifier
          .weight(weight = 1f)
      ) {
        when (it) {
          is BrowserInputLinkActions.Search -> actions.invoke(ToolbarActions.InputSearch(it.url))
          is BrowserInputLinkActions.ClickLoad -> actions.invoke(ToolbarActions.Load(it.url))
          is BrowserInputLinkActions.ClickReload -> actions.invoke(ToolbarActions.Reload)

          is BrowserInputLinkActions.OpenLinkEditor -> {
            linkEditorVisible = it.focused

            if (!it.focused) {
              actions.invoke(ToolbarActions.InputSearchClear)
            }
          }
        }
      }

      ActionsButton(
        linkEditor = linkEditorVisible,
      ) {
        when (it) {
          is ActionsButtonActions.AddTab -> actions.invoke(ToolbarActions.AddTab)
          is ActionsButtonActions.History -> actions.invoke(ToolbarActions.History)

          is ActionsButtonActions.CloseLinkEditor -> {
            keyboardController?.hide()
            focusManager.clearFocus()

            linkEditorVisible = false
          }
        }
      }
    }

    BrowserLinkEditor(
      visible = linkEditorVisible,
      url = url,
      pageIcon = pageIcon,
      title = title,
      onEditLink = { isEditLink += 1 },
      onDismiss = { linkEditorVisible = false },
    )
  }
}

data class BrowserToolbarState(
//  val show: Boolean = true,
  val pageIcon: Bitmap? = null,
  val linkEditorVisible: Boolean = false,
  val toolbarOffset: Float = 0f,
)