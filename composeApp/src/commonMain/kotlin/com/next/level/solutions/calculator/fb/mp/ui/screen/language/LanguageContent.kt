package com.next.level.solutions.calculator.fb.mp.ui.screen.language

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import calculator_fileblocking.composeapp.generated.resources.Res
import calculator_fileblocking.composeapp.generated.resources.done
import calculator_fileblocking.composeapp.generated.resources.select_language
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.nativ.NativeSize
import com.next.level.solutions.calculator.fb.mp.ui.composable.image.Image
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Tick
import com.next.level.solutions.calculator.fb.mp.ui.theme.TextStyleFactory
import org.jetbrains.compose.resources.stringResource

@Composable
fun LanguageContent(
  component: LanguageComponent,
  modifier: Modifier = Modifier,
) {
//  val activateCollapseSecurity by remember { // todo
//    derivedStateOf {
//      component.model.value.activateCollapseSecurity
//    }
//  }

  Content(
    component = component,
    model = component.model.value,
    modifier = modifier,
    action = component::action,
  )

//  if (activateCollapseSecurity) {
//    DisposableEffect(key1 = Unit) {
//      mainStore send MainStore.Event.DeactivateCollapseSecurity
//
//      onDispose {
//        mainStore send MainStore.Event.ActivateCollapseSecurity
//      }
//    }
//  }
}

@Composable
fun LanguageContentPreview(
  modifier: Modifier = Modifier,
  component: LanguageComponent? = null,
  model: LanguageComponent.Model? = null,
) {
    Content(
      component = component,
      model = model,
      modifier = modifier,
    )
}

@Composable
private fun Content(
  component: LanguageComponent?,
  model: LanguageComponent.Model?,
  modifier: Modifier = Modifier,
  action: (LanguageComponent.Action) -> Unit = {},
) {
  val scrollState = rememberScrollState()

  Column(
    verticalArrangement = Arrangement.Top,
    horizontalAlignment = Alignment.Start,
    modifier = modifier
      .background(color = MaterialTheme.colors.background)
      .navigationBarsPadding()
      .statusBarsPadding()
      .fillMaxSize()
  ) {
    Spacer(modifier = Modifier.height(height = 12.dp))

    Row(
      horizontalArrangement = Arrangement.Start,
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier
        .padding(horizontal = 16.dp)
        .fillMaxWidth()
    ) {
      Text(
        text = stringResource(resource = Res.string.select_language),
        style = TextStyleFactory.FS22.w700(),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier
          .weight(weight = 1f)
      )

      Text(
        text = stringResource(resource = Res.string.done),
        style = TextStyleFactory.FS15.w600(),
        modifier = Modifier
          .clip(shape = CircleShape)
          .clickable { action.invoke(LanguageComponent.Action.Done) }
          .background(color = MaterialTheme.colors.primary)
          .padding(vertical = 8.dp, horizontal = 16.dp)
      )
    }

    Spacer(modifier = Modifier.height(height = 18.dp))

    Column(
      verticalArrangement = Arrangement.spacedBy(space = 16.dp),
      horizontalAlignment = Alignment.Start,
      modifier = Modifier
        .weight(weight = 1f)
        .verticalScroll(state = scrollState)
        .padding(horizontal = 16.dp)
        .padding(top = 2.dp, bottom = 36.dp)
    ) {
      model?.languages?.forEach { items ->
        Row(
          horizontalArrangement = Arrangement.spacedBy(space = 12.dp),
          verticalAlignment = Alignment.Top,
          modifier = Modifier
        ) {
          val firstLanguage = items[0]

          LanguageCard(
            language = firstLanguage?.name,
            selected = firstLanguage == model.selected,
          ) {
            if (firstLanguage != null) {
              action.invoke(
                LanguageComponent.Action.ApplyLanguage(value = firstLanguage)
              )
            }
          }

          val secondLanguage = items[1]

          LanguageCard(
            language = secondLanguage?.name,
            selected = secondLanguage == model.selected,
          ) {
            if (secondLanguage != null) {
              action.invoke(
                LanguageComponent.Action.ApplyLanguage(value = secondLanguage)
              )
            }
          }
        }
      }
    }

    component?.nativeAdCard(size = NativeSize.Large)?.invoke(this)
  }
}

@Composable
private fun RowScope.LanguageCard(
  language: String?,
  selected: Boolean,
  onClick: () -> Unit,
) {
  val backgroundColor = when (selected) {
    true -> MaterialTheme.colors.secondary
    false -> MaterialTheme.colors.secondary.copy(alpha = 0.4f)
  }

  when {
    language != null -> Row(
      horizontalArrangement = Arrangement.Start,
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier
        .weight(weight = 1f)
        .clip(shape = MaterialTheme.shapes.large)
        .background(color = backgroundColor)
        .clickable(onClick = onClick)
        .padding(all = 20.dp)
    ) {
      Text(
        text = language,
        color = MaterialTheme.colors.onSecondary,
        style = TextStyleFactory.FS16.w600(),
        maxLines = 1,
        modifier = Modifier
          .weight(weight = 1f)
      )

      Image(
        vector = MagicIcons.All.Tick,
        modifier = Modifier
          .alpha(alpha = if (selected) 1f else 0f)
      )
    }

    else -> Box(
      modifier = Modifier
        .weight(weight = 1f)
    )
  }

}