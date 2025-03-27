package com.next.level.solutions.calculator.fb.mp.ui.screen.calculator.dialog.reset.code

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import calculator_privategallery.composeapp.generated.resources.Res
import calculator_privategallery.composeapp.generated.resources.ob_2
import calculator_privategallery.composeapp.generated.resources.okay
import com.next.level.solutions.calculator.fb.mp.constants.RESET_PASSWORD_CODE
import com.next.level.solutions.calculator.fb.mp.ui.composable.action.button.ActionButton
import com.next.level.solutions.calculator.fb.mp.ui.composable.bottom.sheet.BottomSheet
import com.next.level.solutions.calculator.fb.mp.ui.theme.TextStyleFactory
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetCodeDialogContent(
  component: ResetCodeDialogComponent,
) {
  fun hide() {
    component.action(ResetCodeDialogComponent.Action.Hide)
  }

  BottomSheet(
    onDismissRequest = ::hide,
    content = {
      Content(
        ok = it::dismiss,
      )
    },
  )
}

@Composable
fun ResetCodeDialogContentPreview(
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
  ok: () -> Unit = {},
) {
  Spacer(modifier = Modifier.height(height = 32.dp))

  Text(
    text = stringResource(resource = Res.string.ob_2, RESET_PASSWORD_CODE),
    style = TextStyleFactory.FS28.w700(),
    modifier = Modifier
      .align(alignment = Alignment.CenterHorizontally)
      .padding(horizontal = 34.dp)
  )

  Spacer(modifier = Modifier.height(height = 45.dp))

  ActionButton(
    text = stringResource(Res.string.okay),
    action = ok,
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp)
  )

  Spacer(
    modifier = Modifier
      .navigationBarsPadding()
      .height(height = 16.dp)
  )
}