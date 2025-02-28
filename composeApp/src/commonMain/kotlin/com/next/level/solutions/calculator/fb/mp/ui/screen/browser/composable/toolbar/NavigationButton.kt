package com.next.level.solutions.calculator.fb.mp.ui.screen.browser.composable.toolbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.next.level.solutions.calculator.fb.mp.ui.screen.browser.composable.toolbar.actions.ToolbarActions
import com.next.level.solutions.calculator.fb.mp.ui.composable.image.Image
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Google
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Home

@Composable
internal fun NavigationButton(
  linkEditor: Boolean,
  actions: (ToolbarActions) -> Unit,
) {
  when {
    linkEditor -> Image(
      vector = MagicIcons.All.Google,
      colorFilter = MaterialTheme.colorScheme.onBackground,
      modifier = Modifier
        .size(size = 48.dp)
        .padding(all = 12.dp)
    )

    else -> Image(
      vector = MagicIcons.All.Home,
      colorFilter = MaterialTheme.colorScheme.onBackground,
      modifier = Modifier
        .size(size = 48.dp)
        .padding(all = 2.dp)
        .clip(shape = CircleShape)
        .clickable(onClick = { actions.invoke(ToolbarActions.Home) })
        .padding(all = 10.dp)
    )
  }
}