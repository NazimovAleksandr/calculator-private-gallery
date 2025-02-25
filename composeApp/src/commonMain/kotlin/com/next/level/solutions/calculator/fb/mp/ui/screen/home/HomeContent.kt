package com.next.level.solutions.calculator.fb.mp.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import calculator_fileblocking.composeapp.generated.resources.Res
import calculator_fileblocking.composeapp.generated.resources.calculator_lock
import calculator_fileblocking.composeapp.generated.resources.file
import calculator_fileblocking.composeapp.generated.resources.junk
import calculator_fileblocking.composeapp.generated.resources.notes
import calculator_fileblocking.composeapp.generated.resources.photos
import calculator_fileblocking.composeapp.generated.resources.private_web_browser
import calculator_fileblocking.composeapp.generated.resources.reset
import calculator_fileblocking.composeapp.generated.resources.videos
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.nativ.NativeSize
import com.next.level.solutions.calculator.fb.mp.ui.composable.image.Image
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Browser
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Folder
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Notes
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Photos
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Settings
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Tick
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Trash
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Videos
import com.next.level.solutions.calculator.fb.mp.ui.screen.home.composable.RowCard
import com.next.level.solutions.calculator.fb.mp.ui.theme.TextStyleFactory
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
        text = stringResource(resource = Res.string.calculator_lock),
        style = TextStyleFactory.FS28.w700(),
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

    HorizontalDivider()

    component?.nativeAdCard(
      size = NativeSize.Adaptive,
      modifier = Modifier
        .weight(weight = 1f)
    )

    HorizontalDivider()

    Spacer(modifier = Modifier.height(height = 10.dp))

    Row(
      horizontalArrangement = Arrangement.spacedBy(space = 16.dp),
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier
        .padding(horizontal = 16.dp)
        .fillMaxWidth()
        .clip(shape = MaterialTheme.shapes.extraLarge)
        .background(color = MaterialTheme.colorScheme.secondaryContainer)
        .clickable { component?.action(HomeComponent.Action.Browser) }
        .padding(vertical = 20.dp, horizontal = 24.dp)
    ) {
      Image(
        vector = MagicIcons.All.Browser,
        modifier = Modifier
      )

      Text(
        text = stringResource(resource = Res.string.private_web_browser),
        color = MaterialTheme.colorScheme.onSecondaryContainer,
        style = TextStyleFactory.FS16.w600(),
        modifier = Modifier
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
        text = Res.string.file,
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
        icon = MagicIcons.All.Tick,
        text = Res.string.reset,
        action = { component?.action(HomeComponent.Action.Ad) }
      )

      RowCard(
        icon = MagicIcons.All.Notes,
        text = Res.string.notes,
        action = { component?.action(HomeComponent.Action.Notes) }
      )

      RowCard(
        icon = MagicIcons.All.Trash,
        text = Res.string.junk,
        action = { component?.action(HomeComponent.Action.Trash) }
      )
    }

    Spacer(modifier = Modifier.height(height = 16.dp))
  }
}