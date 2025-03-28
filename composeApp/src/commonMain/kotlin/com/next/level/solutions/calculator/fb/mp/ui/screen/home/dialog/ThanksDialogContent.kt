package com.next.level.solutions.calculator.fb.mp.ui.screen.home.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import calculator_privategallery.composeapp.generated.resources.Res
import calculator_privategallery.composeapp.generated.resources.thank_you_for_helping
import com.next.level.solutions.calculator.fb.mp.ui.composable.action.button.ActionButton
import com.next.level.solutions.calculator.fb.mp.ui.theme.TextStyleFactory
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThanksDialogContent(
  component: ThanksDialogComponent,
) {
  BasicAlertDialog(
    onDismissRequest = {},
    properties = DialogProperties(
      dismissOnBackPress = false,
      dismissOnClickOutside = false,
      usePlatformDefaultWidth = false,
    )
  ) {
    Content(component = component)
  }
}

@Composable
private fun Content(
  component: ThanksDialogComponent,
) {
  Spacer(modifier = Modifier.height(height = 32.dp))

  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
    modifier = Modifier
      .padding(horizontal = 40.dp)
      .clip(shape = MaterialTheme.shapes.extraLarge)
      .background(color = MaterialTheme.colorScheme.surface)
      .padding(vertical = 36.dp, horizontal = 8.dp)
  ) {
    Text(
      text = stringResource(resource = Res.string.thank_you_for_helping),
      style = TextStyleFactory.FS22.w700(),
      textAlign = TextAlign.Center,
      modifier = Modifier
    )

    Spacer(modifier = Modifier.height(height = 24.dp))

    ActionButton(
      text = "OK"
    ) {
      component.action(ThanksDialogComponent.Action.Close)
    }
  }
}