package com.next.level.solutions.calculator.fb.mp.ui.screen.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import calculator_privategallery.composeapp.generated.resources.Res
import calculator_privategallery.composeapp.generated.resources.settings
import calculator_privategallery.composeapp.generated.resources.version
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.next.level.solutions.calculator.fb.mp.entity.ui.extra.SettingsModelUI
import com.next.level.solutions.calculator.fb.mp.entity.ui.extra.SettingsType
import com.next.level.solutions.calculator.fb.mp.expect.PlatformExp
import com.next.level.solutions.calculator.fb.mp.ui.composable.toolbar.Toolbar
import com.next.level.solutions.calculator.fb.mp.ui.screen.settings.composable.SettingsItemButton
import com.next.level.solutions.calculator.fb.mp.ui.screen.settings.composable.SettingsSection
import com.next.level.solutions.calculator.fb.mp.ui.screen.settings.composable.ThemeButton
import com.next.level.solutions.calculator.fb.mp.ui.screen.settings.composable.TipToResetPassword
import com.next.level.solutions.calculator.fb.mp.ui.theme.TextStyleFactory
import kotlinx.collections.immutable.persistentMapOf
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun SettingsContent(
  component: SettingsComponent,
  modifier: Modifier = Modifier,
) {
//  fun logicHandler(signal: Store.Signal) {
//    when (signal) {
//      is SettingsComponent.Signal.Rate5Stars -> context.openMarket()
//      is SettingsComponent.Signal.Share -> context.shareApp()
//    }
//  }

  Content(
    component = component,
    modifier = modifier,
  )
}

@Composable
internal fun SettingsContentPreview(
  modifier: Modifier = Modifier,
) {
  Content(
    component = null,
    modifier = modifier,
  )
}

@Composable
private fun Content(
  component: SettingsComponent?,
  modifier: Modifier = Modifier,
) {
  val model = component?.model?.subscribeAsState()

  val resetCode by remember {
    derivedStateOf {
      model?.value?.resetCode ?: ""
    }
  }

  val tipToResetPassword = model?.value?.tipToResetPassword?.collectAsState(true)

  val items by remember {
    derivedStateOf {
      model?.value?.items ?: persistentMapOf()
    }
  }

  Column(
    verticalArrangement = Arrangement.Top,
    horizontalAlignment = Alignment.Start,
    modifier = modifier
      .background(color = MaterialTheme.colorScheme.background)
      .navigationBarsPadding()
      .statusBarsPadding()
  ) {
    Toolbar(
      title = stringResource(resource = Res.string.settings),
      navAction = { component?.action(SettingsComponent.Action.Back) },
    )

    Column(
      verticalArrangement = Arrangement.spacedBy(space = 4.dp),
      horizontalAlignment = Alignment.Start,
      modifier = Modifier
        .verticalScroll(state = rememberScrollState())
        .weight(weight = 1f)
        .padding(horizontal = 16.dp)
    ) {
      if (tipToResetPassword?.value == true) {
        TipToResetPassword(
          resetCode = resetCode,
          action = { component.action(SettingsComponent.Action.HideTipToResetPassword) },
          modifier = Modifier
        )
      }

      SettingsType.entries.forEach {
        items[it]?.forEachIndexed { index, settingsModelUI: SettingsModelUI ->
          Spacer(modifier = Modifier.height(height = 4.dp))

          if (index == 0) {
            SettingsSection(
              item = it,
              modifier = Modifier
            )
          }

          if (settingsModelUI == SettingsModelUI.Theme) {
            ThemeButton(component = component)
          } else {
            SettingsItemButton(
              component = component,
              settingsModelUI = settingsModelUI,
            )
          }
        }
      }

      Text(
        text = stringResource(resource = Res.string.version) + " " + PlatformExp.appVersion,
        style = TextStyleFactory.FS12.w400(),
        textAlign = TextAlign.Center,
        modifier = Modifier
          .padding(top = 20.dp)
          .fillMaxWidth()
          .alpha(alpha = 0.5f)
      )
    }
  }
}