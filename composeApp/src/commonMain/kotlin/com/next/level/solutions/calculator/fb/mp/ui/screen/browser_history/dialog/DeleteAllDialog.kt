package com.next.level.solutions.calculator.fb.mp.ui.screen.browser_history.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import calculator_fileblocking.composeapp.generated.resources.Res
import calculator_fileblocking.composeapp.generated.resources.are_you_sure_you_want
import calculator_fileblocking.composeapp.generated.resources.cancel
import calculator_fileblocking.composeapp.generated.resources.delete_all
import com.next.level.solutions.calculator.fb.mp.extensions.core.uppercaseFirstChar
import com.next.level.solutions.calculator.fb.mp.ui.composable.action.button.ActionButton
import com.next.level.solutions.calculator.fb.mp.ui.composable.action.button.ButtonColors
import com.next.level.solutions.calculator.fb.mp.ui.composable.bottom.sheet.BottomSheet
import com.next.level.solutions.calculator.fb.mp.ui.composable.image.Image
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Delete
import com.next.level.solutions.calculator.fb.mp.ui.theme.TextStyleFactory
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteAllDialogContent(
  component: DeleteAllDialogComponent,
) {
  fun hide() {
    component.action(DeleteAllDialogComponent.Action.Hide)
  }

  BottomSheet(
    onDismissRequest = ::hide,
    containerColor = MaterialTheme.colorScheme.secondary,
    content = {
      Content(
        actionDismiss = it::dismiss,
        actionAgreed = {
          component.action(DeleteAllDialogComponent.Action.Agreed)
          it.dismiss()
        },
      )
    },
  )
}

@Composable
fun DeleteAllDialogPreview() {
  Column(
    verticalArrangement = Arrangement.Top,
    horizontalAlignment = Alignment.Start,
    modifier = Modifier
      .background(color = MaterialTheme.colorScheme.secondary)
  ) {
    Content()
  }
}

@Composable
private fun ColumnScope.Content(
  actionDismiss: () -> Unit = {},
  actionAgreed: () -> Unit = {},
) {
  Spacer(modifier = Modifier.height(height = 32.dp))

  Image(
    vector = MagicIcons.All.Delete,
    colorFilter = MaterialTheme.colorScheme.error,
    modifier = Modifier
      .size(32.dp)
      .align(alignment = Alignment.CenterHorizontally)
  )

  Spacer(modifier = Modifier.height(height = 24.dp))

  Text(
    text = stringResource(resource = Res.string.delete_all).uppercaseFirstChar(),
    style = TextStyleFactory.FS28.w700(),
    modifier = Modifier
      .align(alignment = Alignment.CenterHorizontally)
  )

  Spacer(modifier = Modifier.height(height = 12.dp))

  Text(
    text = stringResource(resource = Res.string.are_you_sure_you_want),
    style = TextStyleFactory.FS17.w400(),
    textAlign = TextAlign.Center,
    modifier = Modifier
      .align(alignment = Alignment.CenterHorizontally)
      .padding(horizontal = 50.dp)
  )

  Spacer(modifier = Modifier.height(height = 32.dp))

  Row(
    horizontalArrangement = Arrangement.spacedBy(space = 12.dp),
    verticalAlignment = Alignment.Top,
    modifier = Modifier
      .padding(horizontal = 16.dp)
  ) {
    ActionButton(
      text = stringResource(Res.string.cancel),
      colors = ButtonColors.default(
        containerColor = MaterialTheme.colorScheme.surface,
      ),
      action = actionDismiss,
      modifier = Modifier
        .weight(weight = 1f)
    )

    ActionButton(
      text = stringResource(Res.string.delete_all).uppercaseFirstChar(),
      colors = ButtonColors.default(
        containerColor = MaterialTheme.colorScheme.error,
      ),
      action = actionAgreed,
      modifier = Modifier
        .weight(weight = 1f)
    )
  }

  Spacer(modifier = Modifier.height(height = 16.dp))
}