package com.next.level.solutions.calculator.fb.mp.ui.screen.home.dialog

import androidx.compose.foundation.layout.Arrangement
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
import calculator_fileblocking.composeapp.generated.resources.allow
import calculator_fileblocking.composeapp.generated.resources.allow_access_to
import calculator_fileblocking.composeapp.generated.resources.cancel
import calculator_fileblocking.composeapp.generated.resources.need_access
import com.next.level.solutions.calculator.fb.mp.extensions.core.uppercaseFirstChar
import com.next.level.solutions.calculator.fb.mp.ui.composable.action.button.ActionButton
import com.next.level.solutions.calculator.fb.mp.ui.composable.action.button.ButtonColors
import com.next.level.solutions.calculator.fb.mp.ui.composable.bottom.sheet.BottomSheet
import com.next.level.solutions.calculator.fb.mp.ui.composable.image.Image
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Lock
import com.next.level.solutions.calculator.fb.mp.ui.theme.TextStyleFactory
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NeedAccessDialogContent(
  component: NeedAccessDialogComponent,
) {
  fun hide() {
    component.action(NeedAccessDialogComponent.Action.Hide)
  }

  fun agreed() {
    component.action(NeedAccessDialogComponent.Action.Agreed)
  }

  BottomSheet(
    onDismissRequest = ::hide,
    containerColor = MaterialTheme.colorScheme.secondary,
  ) {
    Content(
      actionDismiss = it::dismiss,
      actionAgreed = {
        agreed()
        it.dismiss()
      },
    )
  }
}

@Composable
private fun ColumnScope.Content(
  actionDismiss: () -> Unit = {},
  actionAgreed: () -> Unit = {},
) {
  Spacer(modifier = Modifier.height(height = 32.dp))

  Image(
    vector = MagicIcons.All.Lock,
    modifier = Modifier
      .size(32.dp)
      .align(alignment = Alignment.CenterHorizontally)
  )

  Spacer(modifier = Modifier.height(height = 24.dp))

  Text(
    text = stringResource(resource = Res.string.need_access).uppercaseFirstChar(),
    style = TextStyleFactory.FS28.w700(),
    modifier = Modifier
      .align(alignment = Alignment.CenterHorizontally)
  )

  Spacer(modifier = Modifier.height(height = 12.dp))

  Text(
    text = stringResource(resource = Res.string.allow_access_to),
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
      text = stringResource(Res.string.allow).uppercaseFirstChar(),
      action = actionAgreed,
      modifier = Modifier
        .weight(weight = 1f)
    )
  }

  Spacer(modifier = Modifier.height(height = 16.dp))
}