package com.next.level.solutions.calculator.fb.mp.ui.screen.settings.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import calculator_fileblocking.composeapp.generated.resources.Res
import calculator_fileblocking.composeapp.generated.resources.tip_to_reset_password
import com.next.level.solutions.calculator.fb.mp.ui.composable.reminder.card.ReminderCard

@Composable
internal fun TipToResetPassword(
  resetCode: String,
  modifier: Modifier = Modifier,
  action: () -> Unit,
) {
  Content(
    resetCode = resetCode,
    modifier = modifier,
    action = action,
  )
}

@Composable
private fun Content(
  resetCode: String,
  modifier: Modifier = Modifier,
  @Suppress("UNUSED_PARAMETER") action: () -> Unit = {},
) {
  Box(
    contentAlignment = Alignment.TopEnd,
    modifier = modifier
  ) {
    ReminderCard(
      title = Res.string.tip_to_reset_password,
      description = resetCode,
    )

//    Image(
//      vector = MagicIcons.All.Close,
//      colorFilter = MaterialTheme.colorScheme.onBackground,
//      modifier = Modifier
//        .padding(all = 4.dp)
//        .clip(shape = CircleShape)
//        .clickable(onClick = action)
//        .padding(all = 16.dp)
//        .alpha(alpha = 0.6f)
//    )
  }
}