package com.next.level.solutions.calculator.fb.mp.ui.screen.ad.inter

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import calculator_privategallery.composeapp.generated.resources.Res
import calculator_privategallery.composeapp.generated.resources.ad_will_be_shown
import com.next.level.solutions.calculator.fb.mp.ui.composable.lifecycle.event.listener.LifecycleEventListener
import com.next.level.solutions.calculator.fb.mp.ui.theme.TextStyleFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterDialogContent(
  component: InterDialogComponent,
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
fun InterDialogContentPreview() {
  Box(
    contentAlignment = Alignment.Center,
    modifier = Modifier
      .fillMaxSize()
      .background(color = Color.Black.copy(alpha = 0.4f))
  ) {
    Content()
  }
}

@Composable
private fun Content(
  component: InterDialogComponent? = null,
) {
  var count by remember { mutableIntStateOf(3) }
  var visibleState by remember { mutableStateOf(true) }

  Column(
    verticalArrangement = Arrangement.spacedBy(space = 40.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
      .width(width = 250.dp)
      .background(
        color = MaterialTheme.colorScheme.surface,
        shape = MaterialTheme.shapes.extraLarge,
      )
      .padding(horizontal = 24.dp)
      .padding(vertical = 60.dp)
  ) {
    Text(
      text = stringResource(resource = Res.string.ad_will_be_shown),
      style = TextStyleFactory.FS28.w500(),
      textAlign = TextAlign.Center,
      modifier = Modifier
    )

    Text(
      text = "$count",
      style = TextStyleFactory.FS36.w600(),
      modifier = Modifier
        .size(size = 100.dp)
        .clip(shape = MaterialTheme.shapes.extraLarge)
        .background(color = MaterialTheme.colorScheme.primary)
        .border(width = 2.dp, shape = MaterialTheme.shapes.extraLarge, color = MaterialTheme.colorScheme.onBackground)
        .wrapContentSize()
    )
  }

  LaunchedEffect(key1 = visibleState) {
    launch {
      while (isActive) {
        delay(200)

        if (component?.adsManager?.appOpen?.isShown() == true) {
          component.action(InterDialogComponent.Action.Close)
          return@launch
        }
      }
    }

    if (visibleState) {
      while (count > 0) {
        delay(700)
        count--
      }

      component?.adsManager?.inter?.show {
        component.action(InterDialogComponent.Action.Close)
      }
    }
  }

  LifecycleEventListener(
    onResume = { if (count > 0) visibleState = true },
    onPause = { if (count > 0) visibleState = false },
  )
}