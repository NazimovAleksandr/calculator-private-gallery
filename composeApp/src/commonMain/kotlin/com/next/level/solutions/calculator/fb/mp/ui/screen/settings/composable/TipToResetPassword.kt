package com.next.level.solutions.calculator.fb.mp.ui.screen.settings.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import calculator_privategallery.composeapp.generated.resources.Res
import calculator_privategallery.composeapp.generated.resources.tip_to_reset_password
import com.next.level.solutions.calculator.fb.mp.ui.composable.image.Image
import com.next.level.solutions.calculator.fb.mp.ui.composable.reminder.card.ReminderCard
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Close

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
  action: () -> Unit = {},
) {
  Box(
    contentAlignment = Alignment.TopEnd,
    modifier = modifier
  ) {
    ReminderCard(
      title = Res.string.tip_to_reset_password,
      description = resetCode,
    )

    Image(
      vector = MagicIcons.All.Close,
      colorFilter = MaterialTheme.colorScheme.onBackground,
      modifier = Modifier
        .padding(all = 4.dp)
        .size(size = 40.dp)
        .clip(shape = MaterialTheme.shapes.large)
        .clickable(onClick = action)
        .padding(all = 14.dp)
        .alpha(alpha = 0.4f)
    )
  }
}