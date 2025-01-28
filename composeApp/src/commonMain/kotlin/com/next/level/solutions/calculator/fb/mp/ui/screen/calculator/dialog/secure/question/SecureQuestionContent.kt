package com.next.level.solutions.calculator.fb.mp.ui.screen.calculator.dialog.secure.question

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import calculator_fileblocking.composeapp.generated.resources.Res
import calculator_fileblocking.composeapp.generated.resources.cancel
import calculator_fileblocking.composeapp.generated.resources.done
import calculator_fileblocking.composeapp.generated.resources.security_question
import calculator_fileblocking.composeapp.generated.resources.your_answer
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.next.level.solutions.calculator.fb.mp.ui.composable.action.button.ActionButton
import com.next.level.solutions.calculator.fb.mp.ui.composable.action.button.ButtonColors
import com.next.level.solutions.calculator.fb.mp.ui.composable.bottom.sheet.BottomSheet
import com.next.level.solutions.calculator.fb.mp.ui.composable.text.field.AppTextField
import com.next.level.solutions.calculator.fb.mp.ui.theme.TextStyleFactory
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecureQuestionContent(
  component: SecureQuestionComponent,
) {
  val model by component.model.subscribeAsState()

  val secureQuestion by remember {
    derivedStateOf {
      model.secureQuestion
    }
  }

  fun hide() {
    component.action(SecureQuestionComponent.Action.Hide)
  }

  fun done(answer: String) {
    component.action(SecureQuestionComponent.Action.Done(answer))
  }

  BottomSheet(
    onDismissRequest = ::hide,
    containerColor = MaterialTheme.colorScheme.secondary,
    content = {
      Content(
        question = secureQuestion,
        cancel = it::dismiss,
        done = { answer ->
          done(answer)
          it.dismiss()
        },
      )
    },
  )
}

@Composable
fun SecureQuestionContentPreview(
  modifier: Modifier = Modifier,
) {
  Column(
    verticalArrangement = Arrangement.Top,
    horizontalAlignment = Alignment.Start,
    modifier = modifier
  ) {
    Content()
  }
}

@Composable
private fun ColumnScope.Content(
  question: String = "Question?",
  cancel: () -> Unit = {},
  done: (String) -> Unit = {},
) {
  var answer by remember {
    mutableStateOf("")
  }

  Spacer(modifier = Modifier.height(height = 24.dp))

  Text(
    text = stringResource(resource = Res.string.security_question),
    style = TextStyleFactory.FS20.w600(),
    modifier = Modifier
      .align(alignment = Alignment.CenterHorizontally)
  )

  Spacer(modifier = Modifier.height(height = 40.dp))

  Text(
    text = question,
    style = TextStyleFactory.FS28.w700(),
    modifier = Modifier
      .align(alignment = Alignment.CenterHorizontally)
  )

  Spacer(modifier = Modifier.height(height = 17.dp))

  AppTextField(
    initValue = "",
    placeholder = Res.string.your_answer,
    textStyle = TextStyleFactory.FS16.w600(
      textAlign = TextAlign.Center,
    ),
    onValueChange = { answer = it },
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp)
      .heightIn(max = 60.dp)
      .clip(shape = MaterialTheme.shapes.large)
      .background(color = MaterialTheme.colorScheme.background)
  )

  Spacer(modifier = Modifier.height(height = 48.dp))

  Row(
    horizontalArrangement = Arrangement.End,
    verticalAlignment = Alignment.Top,
    modifier = Modifier
      .fillMaxWidth()
      .navigationBarsPadding()
      .padding(horizontal = 16.dp)
      .padding(bottom = 16.dp)
      .imePadding()
  ) {
    ActionButton(
      text = stringResource(Res.string.cancel),
      colors = ButtonColors.default(
        containerColor = MaterialTheme.colorScheme.surface,
      ),
      action = cancel,
      modifier = Modifier
        .weight(weight = 1f)
    )

    Spacer(modifier = Modifier.width(width = 16.dp))

    ActionButton(
      text = stringResource(Res.string.done),
      action = { done(answer) },
      modifier = Modifier
        .weight(weight = 1f)
    )
  }
}