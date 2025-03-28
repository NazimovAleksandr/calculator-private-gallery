package com.next.level.solutions.calculator.fb.mp.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import calculator_privategallery.composeapp.generated.resources.Res
import calculator_privategallery.composeapp.generated.resources.ads
import calculator_privategallery.composeapp.generated.resources.app_name
import calculator_privategallery.composeapp.generated.resources.browser
import calculator_privategallery.composeapp.generated.resources.files
import calculator_privategallery.composeapp.generated.resources.notes
import calculator_privategallery.composeapp.generated.resources.photos
import calculator_privategallery.composeapp.generated.resources.trash
import calculator_privategallery.composeapp.generated.resources.videos
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.nativ.NativeSize
import com.next.level.solutions.calculator.fb.mp.ui.composable.action.button.ActionButton
import com.next.level.solutions.calculator.fb.mp.ui.composable.action.button.ContentSpace
import com.next.level.solutions.calculator.fb.mp.ui.composable.action.button.IconSize
import com.next.level.solutions.calculator.fb.mp.ui.composable.action.button.IconType
import com.next.level.solutions.calculator.fb.mp.ui.composable.image.Image
import com.next.level.solutions.calculator.fb.mp.ui.composable.lifecycle.event.listener.LifecycleEventListener
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Ads
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Browser
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Folder
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Notes
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Photos
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Settings
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Trash
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Videos
import com.next.level.solutions.calculator.fb.mp.ui.screen.home.composable.RowCard
import com.next.level.solutions.calculator.fb.mp.ui.theme.TextStyleFactory
import com.next.level.solutions.calculator.fb.mp.utils.withNotNull
import org.jetbrains.compose.resources.stringResource

@Composable
fun HomeContent(
  component: HomeComponent,
  modifier: Modifier = Modifier,
) {
  Content(
    component = component,
    modifier = modifier,
  )

  LifecycleEventListener(
    onStart = { component.action(HomeComponent.Action.OnStart) },
  )
}

@Composable
fun HomeContentPreview(
  modifier: Modifier = Modifier,
) {
  Content(
    component = null,
    modifier = modifier,
  )
}

@Composable
private fun Content(
  component: HomeComponent?,
  modifier: Modifier = Modifier,
) {
  Column(
    verticalArrangement = Arrangement.Top,
    horizontalAlignment = Alignment.Start,
    modifier = modifier
      .background(color = MaterialTheme.colorScheme.background)
      .navigationBarsPadding()
      .statusBarsPadding()
      .fillMaxSize()
  ) {
    Row(
      horizontalArrangement = Arrangement.Start,
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier
        .padding(vertical = 12.dp, horizontal = 16.dp)
        .fillMaxWidth()
    ) {
      Text(
        text = stringResource(resource = Res.string.app_name),
        style = TextStyleFactory.FS22.w700(),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier
          .weight(weight = 1f)
      )

      Image(
        vector = MagicIcons.All.Settings,
        colorFilter = MaterialTheme.colorScheme.onSecondary,
        modifier = Modifier
          .clip(shape = CircleShape)
          .background(color = MaterialTheme.colorScheme.secondary)
          .clickable { component?.action(HomeComponent.Action.Settings) }
          .padding(all = 8.dp)
      )
    }

    withNotNull(component) {
      nativeAdCard(
        size = NativeSize.Adaptive,
        modifier = Modifier
          .weight(weight = 1f)
      )
    }

    Spacer(modifier = Modifier.height(height = 10.dp))

    if (component?.adsManager?.state == true) {
      val interState by remember {
        component.adsManager.inter.state()
      }

      ActionButton(
        enabled = interState,
        text = stringResource(resource = Res.string.ads),
        textMaxLines = Int.MAX_VALUE,
        style = TextStyleFactory.FS16.w600(),
        iconStart = MagicIcons.All.Ads,
        iconType = IconType.Image,
        iconStartSize = IconSize(size = 44.dp),
        contentSpace = ContentSpace(horizontal = 36.dp),
        paddingValues = PaddingValues(vertical = 20.dp, horizontal = 32.dp),
        action = { component.action(HomeComponent.Action.Ad) },
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp)
      )

      Spacer(modifier = Modifier.height(height = 10.dp))
    }

    Row(
      horizontalArrangement = Arrangement.spacedBy(space = 12.dp),
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier
        .padding(horizontal = 16.dp)
    ) {
      RowCard(
        icon = MagicIcons.All.Photos,
        text = Res.string.photos,
        action = { component?.action(HomeComponent.Action.Photos) }
      )

      RowCard(
        icon = MagicIcons.All.Videos,
        text = Res.string.videos,
        action = { component?.action(HomeComponent.Action.Videos) }
      )

      RowCard(
        icon = MagicIcons.All.Folder,
        text = Res.string.files,
        action = { component?.action(HomeComponent.Action.Files) }
      )
    }

    Spacer(modifier = Modifier.height(height = 10.dp))

    Row(
      horizontalArrangement = Arrangement.spacedBy(space = 12.dp),
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier
        .padding(horizontal = 16.dp)
    ) {
      RowCard(
        icon = MagicIcons.All.Browser,
        text = Res.string.browser,
        action = { component?.action(HomeComponent.Action.Browser) }
      )

      RowCard(
        icon = MagicIcons.All.Notes,
        text = Res.string.notes,
        action = { component?.action(HomeComponent.Action.Notes) }
      )

      RowCard(
        icon = MagicIcons.All.Trash,
        text = Res.string.trash,
        action = { component?.action(HomeComponent.Action.Trash) }
      )
    }

    Spacer(modifier = Modifier.height(height = 16.dp))
  }
}