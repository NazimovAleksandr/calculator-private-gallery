package com.next.level.solutions.calculator.fb.mp.ui.screen.security.question

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import calculator_fileblocking.composeapp.generated.resources.Res
import calculator_fileblocking.composeapp.generated.resources.continue_
import calculator_fileblocking.composeapp.generated.resources.enter_secret_word
import calculator_fileblocking.composeapp.generated.resources.security_question
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.next.level.solutions.calculator.fb.mp.ui.screen.security.question.composable.Answer
import com.next.level.solutions.calculator.fb.mp.ui.screen.security.question.composable.ButtonSkip
import com.next.level.solutions.calculator.fb.mp.ui.screen.security.question.composable.Questions
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.nativ.NativeSize
import com.next.level.solutions.calculator.fb.mp.ui.composable.action.button.ActionButton
import com.next.level.solutions.calculator.fb.mp.ui.theme.TextStyleFactory
import org.jetbrains.compose.resources.stringResource

@Composable
fun SecureQuestionContent(
  component: SecureQuestionComponent,
  modifier: Modifier = Modifier,
) {
  Content(
    component = component,
    modifier = modifier,
  )
}

@Composable
fun SecureQuestionContentPreview(
  modifier: Modifier = Modifier,
) {
  Content(
    component = null,
    modifier = modifier,
  )
}

@Composable
private fun Content(
  component: SecureQuestionComponent?,
  modifier: Modifier = Modifier,
) {
  val keyboardController = LocalSoftwareKeyboardController.current
  val focusManager = LocalFocusManager.current

  val model = component?.model?.subscribeAsState()

  val initQuestion by remember {
    derivedStateOf {
      model?.value?.initQuestion
    }
  }

  val initAnswer by remember {
    derivedStateOf {
      model?.value?.initAnswer
    }
  }

  val answerState by remember {
    derivedStateOf {
      model?.value?.answerState ?: false
    }
  }

  val density = LocalDensity.current

  var imePadding by remember {
    mutableIntStateOf(0)
  }

  var isImeVisible by remember {
    mutableStateOf(false)
  }

  var windowInsetsImeVisible by remember {
    mutableStateOf(false)
  }

  val keyboardHeight = WindowInsets.ime.getBottom(LocalDensity.current)

  LaunchedEffect(key1 = keyboardHeight) {
    windowInsetsImeVisible = keyboardHeight > 0
  }

  LaunchedEffect(key1 = windowInsetsImeVisible) {
    isImeVisible = windowInsetsImeVisible
  }

  Column(
    verticalArrangement = Arrangement.Top,
    horizontalAlignment = Alignment.Start,
    modifier = modifier
      .background(color = MaterialTheme.colorScheme.background)
      .statusBarsPadding()
      .navigationBarsPadding()
  ) {
    Spacer(modifier = Modifier.height(height = 12.dp))

    Row(
      horizontalArrangement = Arrangement.Start,
      verticalAlignment = Alignment.Bottom,
      modifier = Modifier
        .padding(horizontal = 16.dp)
    ) {
      Text(
        text = stringResource(resource = Res.string.security_question),
        style = TextStyleFactory.FS28.w700(),
        modifier = Modifier
          .weight(weight = 1f)
      )

      ButtonSkip {
        component?.action(SecureQuestionComponent.Action.Skip)
      }
    }

    Spacer(modifier = Modifier.height(height = 5.dp))

    Text(
      text = stringResource(resource = Res.string.enter_secret_word),
      color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
      style = TextStyleFactory.FS17.w400(),
      modifier = Modifier
        .padding(horizontal = 16.dp)
    )

    Spacer(
      modifier = Modifier
        .animateContentSize(
          animationSpec = tween(
            durationMillis = 70,
          )
        )
        .height(
          height = when {
            isImeVisible -> 16.dp
            else -> 48.dp
          }
        )
    )

    Questions(
      initQuestion = initQuestion,
      selected = { component?.action(SecureQuestionComponent.Action.Question(it)) },
      modifier = Modifier
        .padding(horizontal = 16.dp)
    )

    Spacer(modifier = Modifier.height(height = 16.dp))

    Answer(
      initAnswer = initAnswer,
      action = { component?.action(SecureQuestionComponent.Action.Answer(it)) },
    )

    Spacer(modifier = Modifier.height(height = 16.dp))

    ActionButton(
      text = stringResource(resource = Res.string.continue_),
      enabled = answerState,
      action = {
        keyboardController?.hide()
        focusManager.clearFocus()

        component?.action(SecureQuestionComponent.Action.SaveAnswer)
      },
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

    Spacer(
      modifier = Modifier
        .onGloballyPositioned {
          when {
            imePadding == 0 -> imePadding = it.size.height

            !isImeVisible
                && it.size.height > imePadding
              -> isImeVisible = true

            isImeVisible
                && it.size.height < imePadding
                && it.size.height < with(density) { 260.dp.toPx() }
              -> isImeVisible = false
          }

          imePadding = it.size.height
        }
        .imePadding()
        .size(size = 1.dp)
    )
  }
}