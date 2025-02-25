package com.next.level.solutions.calculator.fb.mp.ui.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.next.level.solutions.calculator.fb.mp.ui.screen.calculator.CalculatorContentPreview
import com.next.level.solutions.calculator.fb.mp.ui.screen.calculator.composable.CalculatorButtonsPreview
import com.next.level.solutions.calculator.fb.mp.ui.screen.calculator.composable.CreatingPassword
import com.next.level.solutions.calculator.fb.mp.ui.screen.calculator.dialog.reset.code.ResetCodeDialogContentPreview
import com.next.level.solutions.calculator.fb.mp.ui.screen.calculator.dialog.secure.question.SecureQuestionDialogContentPreview
import com.next.level.solutions.calculator.fb.mp.ui.theme.AppThemePreview

@PreviewLightDark
@Composable
private fun Preview() {
  AppThemePreview {
    CalculatorContentPreview()
  }
}

@PreviewLightDark
@Composable
private fun CalculatorButtons_Preview() {
  AppThemePreview {
    CalculatorButtonsPreview(
      creatingPassword = remember { mutableStateOf(true) },
      enteredNumberLength = remember { mutableStateOf(true) },
    )
  }
}

@PreviewLightDarkHeight200
@Composable
private fun CreatingPassword_Preview() {
  val isConfirmPassword = isSystemInDarkTheme()

  AppThemePreview {
    Column(
      verticalArrangement = Arrangement.Top,
      horizontalAlignment = Alignment.Start,
      modifier = Modifier
    ) {
      CreatingPassword(
        isConfirmPassword = isConfirmPassword,
      )
    }
  }
}

@PreviewLightDark
@Composable
private fun ResetPasswordCodeDialog_Preview() {
  AppThemePreview {
    ResetCodeDialogContentPreview(
      modifier = Modifier
        .background(color = MaterialTheme.colorScheme.secondary)
    )
  }
}

@PreviewLightDark
@Composable
private fun SecureQuestionDialog_Preview() {
  AppThemePreview {
    SecureQuestionDialogContentPreview(
      modifier = Modifier
        .background(color = MaterialTheme.colorScheme.secondary)
    )
  }
}