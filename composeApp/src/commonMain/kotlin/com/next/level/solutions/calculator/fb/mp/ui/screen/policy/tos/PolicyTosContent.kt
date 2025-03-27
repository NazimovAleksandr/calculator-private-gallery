package com.next.level.solutions.calculator.fb.mp.ui.screen.policy.tos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import calculator_privategallery.composeapp.generated.resources.Res
import calculator_privategallery.composeapp.generated.resources.privacy_policy
import calculator_privategallery.composeapp.generated.resources.tos
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState
import com.next.level.solutions.calculator.fb.mp.ui.composable.toolbar.Toolbar
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun PolicyTosContent(
  component: PolicyTosComponent,
  modifier: Modifier = Modifier,
) {
  Content(
    component = component,
    modifier = modifier,
  )
}

@Composable
internal fun PolicyTosContentPreview(
  modifier: Modifier = Modifier,
) {
  Content(
    component = null,
    modifier = modifier,
  )
}

@Composable
private fun Content(
  component: PolicyTosComponent?,
  modifier: Modifier = Modifier,
) {
  val model = component?.model?.subscribeAsState()

  val tos by remember {
    derivedStateOf {
      model?.value?.tos
    }
  }

  val webViewState = rememberWebViewState(
    url = when (tos) {
      true -> "https://www.freeprivacypolicy.com/live/fc847603-f386-48ff-8eba-8ec01998433e"
      else -> "https://www.freeprivacypolicy.com/live/da3b9a34-859c-4028-a2bb-fb02fdf10c6f"
    },
  )

  Column(
    verticalArrangement = Arrangement.Top,
    horizontalAlignment = Alignment.Start,
    modifier = modifier
      .background(color = MaterialTheme.colorScheme.background)
      .statusBarsPadding()
      .navigationBarsPadding()
  ) {
    Toolbar(
      title = stringResource(
        resource = when (tos) {
          true -> Res.string.tos
          else -> Res.string.privacy_policy
        },
      ),
      navAction = { component?.action(PolicyTosComponent.Action.Back) },
    )

    WebView(
      state = webViewState,
      modifier = Modifier
        .fillMaxWidth()
        .weight(weight = 1f)
    )
  }
}