package com.next.level.solutions.calculator.fb.mp.ui.screen.browser.composable.toolbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.next.level.solutions.calculator.fb.mp.ui.composable.image.Image
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.AddTab
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.History

interface ActionsButtonActions {
  object CloseLinkEditor : ActionsButtonActions
  object AddTab : ActionsButtonActions
  object History : ActionsButtonActions
}

@Composable
internal fun ActionsButton(
  linkEditor: Boolean,
  actions: (ActionsButtonActions) -> Unit,
) {
  when {
    linkEditor -> Spacer(
      modifier = Modifier
        .width(width = 12.dp)
    )

    else -> {
      Spacer(
        modifier = Modifier
          .width(width = 4.dp)
      )

      Image(
        vector = MagicIcons.All.AddTab,
        colorFilter = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier
          .size(size = 48.dp)
          .padding(all = 2.dp)
          .clip(shape = CircleShape)
          .clickable(onClick = { actions.invoke(ActionsButtonActions.AddTab) })
          .padding(all = 10.dp)
      )

      Spacer(
        modifier = Modifier
          .width(width = 4.dp)
      )

      Image(
        vector = MagicIcons.All.History,
        colorFilter = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier
          .size(size = 48.dp)
          .padding(all = 2.dp)
          .clip(shape = CircleShape)
          .clickable(onClick = { actions.invoke(ActionsButtonActions.History) })
          .padding(all = 10.dp)
      )

      Spacer(
        modifier = Modifier
          .width(width = 4.dp)
      )
    }
  }
}