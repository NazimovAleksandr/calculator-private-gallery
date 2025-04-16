package com.next.level.solutions.calculator.fb.mp.ui.screen.settings.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import calculator_privategallery.composeapp.generated.resources.Res
import calculator_privategallery.composeapp.generated.resources.theme
import com.adamglin.composeshadow.dropShadow
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.next.level.solutions.calculator.fb.mp.ui.composable.image.Image
import com.next.level.solutions.calculator.fb.mp.ui.composable.toggle.button.ToggleButton
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Dark
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Light
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent
import com.next.level.solutions.calculator.fb.mp.ui.screen.settings.SettingsComponent
import com.next.level.solutions.calculator.fb.mp.ui.theme.TextStyleFactory
import org.jetbrains.compose.resources.stringResource

@Composable
fun ThemeButton(
  component: SettingsComponent?,
  modifier: Modifier = Modifier
) {
  val model: State<SettingsComponent.Model>? = component?.model?.subscribeAsState()
  val rootModel: State<RootComponent.Model>? = model?.value?.rootModel?.subscribeAsState()

  val darkTheme by remember {
    derivedStateOf {
      rootModel?.value?.darkTheme ?: true
    }
  }

  Row(
    horizontalArrangement = Arrangement.spacedBy(space = 10.dp, alignment = Alignment.CenterHorizontally),
    verticalAlignment = Alignment.CenterVertically,
    modifier = modifier
      .dropShadow(
        shape = MaterialTheme.shapes.large,
        offsetX = 3.dp,
        offsetY = 3.dp,
      )
      .clip(shape = MaterialTheme.shapes.large)
      .background(color = MaterialTheme.colorScheme.secondary)
      .padding(vertical = 2.dp)
      .padding(all = 16.dp)
  ) {
    Image(
      vector = MagicIcons.All.Light,
      contentDescription = null,
      colorFilter = MaterialTheme.colorScheme.primary,
      modifier = Modifier
        .size(size = 22.dp)
    )

    Text(
      text = stringResource(resource = Res.string.theme),
      style = TextStyleFactory.FS17.w400(),
      color = MaterialTheme.colorScheme.onSecondary,
      modifier = Modifier.weight(weight = 1f)
    )

    ToggleButton(
      initChecked = darkTheme,
      backgroundColor = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.05f),
      thumb = {
        Image(
          vector = if (darkTheme) MagicIcons.All.Dark else MagicIcons.All.Light,
          modifier = Modifier
            .height(height = 24.dp)
            .width(width = 30.dp)
            .clip(shape = CircleShape)
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(start = if (darkTheme) 1.8.dp else 0.dp)
            .padding(vertical = 3.dp, horizontal = 6.dp)
        )
      },
      onCheckedChange = { isDarkTheme ->
        component?.action(SettingsComponent.Action.ChangeTheme(darkTheme = isDarkTheme))
      }
    )
  }
}