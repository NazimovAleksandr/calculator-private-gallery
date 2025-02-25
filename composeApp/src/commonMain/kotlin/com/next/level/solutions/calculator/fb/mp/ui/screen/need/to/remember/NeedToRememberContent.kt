package com.next.level.solutions.calculator.fb.mp.ui.screen.need.to.remember

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import calculator_fileblocking.composeapp.generated.resources.Res
import calculator_fileblocking.composeapp.generated.resources.i_got_it
import calculator_fileblocking.composeapp.generated.resources.need_to_remember
import calculator_fileblocking.composeapp.generated.resources.security_question
import calculator_fileblocking.composeapp.generated.resources.tip_to_reset_password
import calculator_fileblocking.composeapp.generated.resources.your_answer
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.nativ.NativeSize
import com.next.level.solutions.calculator.fb.mp.ui.composable.action.button.ActionButton
import com.next.level.solutions.calculator.fb.mp.ui.composable.reminder.card.ReminderCard
import com.next.level.solutions.calculator.fb.mp.ui.theme.TextStyleFactory
import org.jetbrains.compose.resources.stringResource

@Composable
fun NeedToRememberContent(
  component: NeedToRememberComponent,
  modifier: Modifier = Modifier,
) {
  Content(
    component = component,
    modifier = modifier,
  )
}

@Composable
fun NeedToRememberContentPreview(
  modifier: Modifier = Modifier,
) {
  Content(
    component = null,
    modifier = modifier,
  )
}

@Composable
private fun Content(
  component: NeedToRememberComponent?,
  modifier: Modifier = Modifier,
) {
  val model = component?.model?.subscribeAsState()

  Column(
    verticalArrangement = Arrangement.Top,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier
      .background(color = MaterialTheme.colorScheme.background)
      .statusBarsPadding()
      .navigationBarsPadding()
  ) {
    Spacer(modifier = Modifier.height(height = 66.dp))

    Text(
      text = stringResource(resource = Res.string.need_to_remember),
      style = TextStyleFactory.FS28.w700(),
      textAlign = TextAlign.Center,
      modifier = Modifier
        .padding(horizontal = 16.dp)
    )

    Spacer(modifier = Modifier.height(height = 24.dp))

    ReminderCard(
      title = Res.string.security_question,
      description = model?.value?.question.toString(),
      modifier = Modifier
        .padding(horizontal = 16.dp)
    )

    Spacer(modifier = Modifier.height(height = 24.dp))

    ReminderCard(
      title = Res.string.your_answer,
      description = model?.value?.answer.toString(),
      modifier = Modifier
        .padding(horizontal = 16.dp)
    )

    Spacer(modifier = Modifier.height(height = 24.dp))

    ReminderCard(
      title = Res.string.tip_to_reset_password,
      description = model?.value?.resetCode.toString(),
      modifier = Modifier
        .padding(horizontal = 16.dp)
    )

    Spacer(modifier = Modifier.weight(weight = 1f))

    ActionButton(
      text = stringResource(resource = Res.string.i_got_it),
      action = { component?.action(NeedToRememberComponent.Action.OK) },
      modifier = Modifier
        .padding(horizontal = 50.dp)
        .fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(height = 16.dp))

    HorizontalDivider()

    component?.nativeAdCard(
      size = NativeSize.Small,
    )

    HorizontalDivider()
  }
}