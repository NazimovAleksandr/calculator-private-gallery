package com.next.level.solutions.calculator.fb.mp.ui.screen.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.nativ.NativeSize
import com.next.level.solutions.calculator.fb.mp.ui.screen.calculator.composable.CalculatorButtons
import com.next.level.solutions.calculator.fb.mp.ui.screen.calculator.composable.CreatingPassword
import com.next.level.solutions.calculator.fb.mp.ui.screen.calculator.composable.TextAutoSize
import com.next.level.solutions.calculator.fb.mp.ui.theme.TextStyleFactory

@Composable
fun CalculatorContent(
  component: CalculatorComponent,
  modifier: Modifier = Modifier,
) {
  Content(
    component = component,
    modifier = modifier,
  )
}

@Composable
fun CalculatorContentPreview(
  modifier: Modifier = Modifier,
) {
    Content(
      component = null,
      modifier = modifier,
    )
}

@Composable
private fun Content(
  component: CalculatorComponent?,
  modifier: Modifier = Modifier,
) {
  val model = component?.model?.subscribeAsState()

  val creatingPassword = remember {
    derivedStateOf {
      model?.value?.creatingPassword ?: false
    }
  }

  val creatingPassword2 = remember {
    derivedStateOf {
      false
    }
  }

  val password by remember {
    derivedStateOf {
      model?.value?.password ?: ""
    }
  }

  val allNumber by remember {
    derivedStateOf {
      model?.value?.allNumber ?: ""
    }
  }

  val enteredNumber by remember {
    derivedStateOf {
      model?.value?.enteredNumber ?: ""
    }
  }

  /*val enteredNumberLength = remember(state) {
    derivedStateOf {
      state.value.enteredNumber.length >= 4
    }
  }*/

  val enteredNumberLength2 = remember {
    derivedStateOf {
      true
    }
  }

  val isConfirmPassword by remember {
    derivedStateOf {
      password.isNotEmpty()
    }
  }

  Column(
    verticalArrangement = Arrangement.Bottom,
    horizontalAlignment = Alignment.End,
    modifier = modifier
      .background(color = MaterialTheme.colorScheme.background)
      .statusBarsPadding()
      .navigationBarsPadding()
      .pointerInput(key1 = Unit) {}
  ) {
    Spacer(modifier = Modifier.height(height = 16.dp))

    if (creatingPassword.value) {
      CreatingPassword(
        isConfirmPassword = isConfirmPassword,
        modifier = Modifier
          .padding(horizontal = 16.dp)
      )

      Spacer(modifier = Modifier.weight(weight = 1f))
    } else {
      Text(
        text = allNumber,
        overflow = TextOverflow.Ellipsis,
        style = TextStyleFactory.FS36.w400(),
        textAlign = TextAlign.End,
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 20.dp)
          .weight(weight = 1f)
      )
    }

    TextAutoSize(
      text = enteredNumber,
      style = TextStyleFactory.FS90.w300(
        textAlign = TextAlign.End,
      ),
      modifier = Modifier
        .padding(horizontal = 20.dp)
        .align(alignment = Alignment.End)
    )

    Spacer(modifier = Modifier.height(height = 24.dp))

    CalculatorButtons(
      creatingPassword = creatingPassword2,
      enteredNumberLength = enteredNumberLength2,
      action = { component?.action(CalculatorComponent.Action.CalculatorButtonClick(it)) },
      modifier = Modifier
        .padding(horizontal = 18.dp)
    )

    Spacer(modifier = Modifier.height(height = 24.dp))

    component?.nativeAdCard(size = NativeSize.Small)?.invoke(this)
  }
}