package com.next.level.solutions.calculator.fb.mp.ui.screen.browser_history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import calculator_privategallery.composeapp.generated.resources.Res
import calculator_privategallery.composeapp.generated.resources.delete_all
import calculator_privategallery.composeapp.generated.resources.history
import calculator_privategallery.composeapp.generated.resources.your_history_is_empty
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.nativ.NativeSize
import com.next.level.solutions.calculator.fb.mp.ui.composable.action.button.ActionButton
import com.next.level.solutions.calculator.fb.mp.ui.composable.action.button.ButtonColors
import com.next.level.solutions.calculator.fb.mp.ui.composable.toolbar.Toolbar
import com.next.level.solutions.calculator.fb.mp.ui.screen.browser_history.composable.HistoryCard
import com.next.level.solutions.calculator.fb.mp.ui.screen.browser_history.model.HistoryItem
import com.next.level.solutions.calculator.fb.mp.ui.theme.TextStyleFactory
import com.next.level.solutions.calculator.fb.mp.utils.withNotNull
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun BrowserHistoryContent(
  component: BrowserHistoryComponent,
  modifier: Modifier = Modifier,
) {
  val dialog by component.dialog.subscribeAsState()

  Content(
    component = component,
    modifier = modifier,
  )

  dialog.child?.instance?.content()
}

@Composable
fun BrowserHistoryContentPreview() {
  Content(null)
}

@Composable
private fun Content(
  component: BrowserHistoryComponent?,
  modifier: Modifier = Modifier,
) {
  val model = component?.model?.subscribeAsState()

  val itemsValue: State<ImmutableList<HistoryItem>>? = model?.value?.items?.collectAsStateWithLifecycle(
    initialValue = persistentListOf(),
  )

  Column(
    verticalArrangement = Arrangement.Top,
    horizontalAlignment = Alignment.Start,
    modifier = modifier
      .pointerInput(key1 = Unit) {}
      .background(color = MaterialTheme.colorScheme.background)
      .navigationBarsPadding()
      .statusBarsPadding()
  ) {
    Toolbar(
      title = stringResource(resource = Res.string.history),
      navAction = { component?.action(BrowserHistoryComponent.Action.Back) },
    )

    when {
      itemsValue?.value == null -> Box(
        contentAlignment = Alignment.Center,
        content = { LinearProgressIndicator() },
        modifier = Modifier
          .weight(weight = 1f)
          .fillMaxWidth()
      )

      itemsValue.value.isEmpty() -> {
        Box(
          contentAlignment = Alignment.Center,
          modifier = Modifier
            .weight(weight = 1f)
        ) {
          Text(
            text = stringResource(resource = Res.string.your_history_is_empty),
            style = TextStyleFactory.FS22.w700(textAlign = TextAlign.Center),
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 24.dp)
          )
        }

        withNotNull(component) {
          nativeAdCard(
            size = NativeSize.Large,
          )
        }
      }

      else -> {
        LazyColumn(
          verticalArrangement = Arrangement.spacedBy(space = 2.dp),
          contentPadding = PaddingValues(top = 0.dp, bottom = 104.dp),
          modifier = Modifier
            .weight(weight = 1f)
        ) {
          items(
            items = itemsValue.value,
            key = { "${it.date}${it.data?.id}${it.data?.title}" },
          ) {
            HistoryCard(
              item = it,
              action = { action ->
                when (action) {
                  is HistoryCard.Open -> {
                    component.action(BrowserHistoryComponent.Action.OpenItem(action.item))
                    component.action(BrowserHistoryComponent.Action.Back)
                  }

                  is HistoryCard.Delete -> component.action(
                    BrowserHistoryComponent.Action.DeleteItem(action.item)
                  )
                }
              },
              modifier = Modifier.animateItem()
            )
          }
        }

        HorizontalDivider()

        ActionButton(
          text = stringResource(resource = Res.string.delete_all),
          action = { component.action(BrowserHistoryComponent.Action.DeleteAllDialog) },
          colors = ButtonColors.default(
            containerColor = MaterialTheme.colorScheme.error,
          ),
          modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp)
        )

        withNotNull(component) {
          nativeAdCard(
            size = NativeSize.Small,
          )
        }
      }
    }
  }
}