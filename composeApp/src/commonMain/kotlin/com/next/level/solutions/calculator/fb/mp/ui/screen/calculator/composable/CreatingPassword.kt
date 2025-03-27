package com.next.level.solutions.calculator.fb.mp.ui.screen.calculator.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import calculator_privategallery.composeapp.generated.resources.Res
import calculator_privategallery.composeapp.generated.resources.confirm_password
import calculator_privategallery.composeapp.generated.resources.creating_new_password
import calculator_privategallery.composeapp.generated.resources.enter_four_digit
import calculator_privategallery.composeapp.generated.resources.re_enter_your_password
import com.next.level.solutions.calculator.fb.mp.ui.theme.TextStyleFactory
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun CreatingPassword(
  isConfirmPassword: Boolean,
  modifier: Modifier = Modifier,
) {
  Text(
    text = stringResource(
      resource = when (isConfirmPassword) {
        true -> Res.string.confirm_password
        else -> Res.string.creating_new_password
      },
    ),
    style = TextStyleFactory.FS28.w700(),
    modifier = modifier
      .fillMaxWidth()
  )

  Text(
    text = stringResource(
      resource = when (isConfirmPassword) {
        true -> Res.string.re_enter_your_password
        else -> Res.string.enter_four_digit
      },
    ),
    style = TextStyleFactory.FS17.w400(),
    modifier = modifier
      .fillMaxWidth()
      .alpha(alpha = 0.5f)
  )
}