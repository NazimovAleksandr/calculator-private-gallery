package com.next.level.solutions.calculator.fb.mp.ui.screen.browser_history.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import calculator_fileblocking.composeapp.generated.resources.Res
import calculator_fileblocking.composeapp.generated.resources.ic_placeholder_icon
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import com.next.level.solutions.calculator.fb.mp.entity.ui.BrowserHistoryUI
import com.next.level.solutions.calculator.fb.mp.expect.getDateFormat
import com.next.level.solutions.calculator.fb.mp.ui.composable.image.Image
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Delete
import com.next.level.solutions.calculator.fb.mp.ui.screen.browser_history.model.HistoryItem
import com.next.level.solutions.calculator.fb.mp.ui.theme.TextStyleFactory
import io.ktor.http.Url
import okio.FileSystem

sealed interface HistoryCard {
  class Open(val item: BrowserHistoryUI) : HistoryCard
  class Delete(val item: BrowserHistoryUI) : HistoryCard
}

@Composable
internal fun HistoryCard(
  item: HistoryItem,
  modifier: Modifier = Modifier,
  action: (HistoryCard) -> Unit,
) {
  if (item.data == null) {
    Date(
      date = item.date,
      modifier = modifier,
    )
  } else {
    Content(
      item = item.data,
      modifier = modifier,
      action = action,
    )
  }
}

@Composable
fun HistoryCardPreview() {
  val dateFormat = getDateFormat("dd MMM yyyy")

  val list = listOf(
    BrowserHistoryUI(
      title = "Willaim",
      url = "Maribel",
      date = "Ashton",
      time = 2011L,
    ),
    BrowserHistoryUI(
      title = "Willaim",
      url = "Maribel",
      date = "Ashton",
      time = 2011L,
    ),
    BrowserHistoryUI(
      title = "Willaim",
      url = "Maribel",
      date = "Ashton",
      time = 2011L,
    ),
  ).sortedByDescending {
    it.time
  }.groupBy {
    dateFormat.format(it.time)
  }.flatMap { (key, values) ->
    mutableListOf(HistoryItem(date = key, data = null)).apply {
      addAll(values.map { HistoryItem(date = key, data = it) })
    }
  }

  Column(
    horizontalAlignment = Alignment.Start,
    verticalArrangement = Arrangement.spacedBy(space = 4.dp),
    modifier = Modifier
  ) {
    list.forEach {
      HistoryCard(
        item = it,
        action = {},
      )
    }
  }
}

@Composable
private fun Content(
  item: BrowserHistoryUI,
  modifier: Modifier = Modifier,
  action: (HistoryCard) -> Unit = {},
) {
  val host = Url(item.url).host

  fun onOpenUrl() {
    action(HistoryCard.Open(item))
  }

  fun onDeleteItem() {
    action(HistoryCard.Delete(item))
  }

  Row(
    horizontalArrangement = Arrangement.spacedBy(space = 10.dp),
    verticalAlignment = Alignment.CenterVertically,
    modifier = modifier
      .background(color = MaterialTheme.colorScheme.secondaryContainer)
      .clickable(onClick = ::onOpenUrl)
      .padding(vertical = 10.dp)
      .padding(start = 16.dp)
  ) {
    SiteIcon(
      host = host,
    )

    Spacer(Modifier.width(width = 14.dp))

    Column(
      horizontalAlignment = Alignment.Start,
      modifier = Modifier
        .weight(weight = 1f)
    ) {
      Text(
        text = item.title,
        style = TextStyleFactory.FS16.w600(),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier
      )

      Text(
        text = host,
        style = TextStyleFactory.FS12.w400(),
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
        maxLines = 1,
        modifier = Modifier
      )
    }

    Image(
      vector = MagicIcons.All.Delete,
      colorFilter = MaterialTheme.colorScheme.error,
      modifier = Modifier
        .padding(end = 4.dp)
        .size(size = 48.dp)
        .clip(shape = MaterialTheme.shapes.large)
        .clickable(onClick = ::onDeleteItem)
        .padding(all = 12.dp)
    )
  }
}

@Composable
private fun Date(
  date: String,
  modifier: Modifier = Modifier,
) {
  Text(
    text = date,
    style = TextStyleFactory.FS17.w400(),
    modifier = modifier
      .fillMaxWidth()
      .padding(all = 16.dp)
      .alpha(alpha = 0.6f)
  )
}

@Composable
private fun SiteIcon(
  host: String,
  modifier: Modifier = Modifier,
) {
  val platformContext = LocalPlatformContext.current

  val pageIconRequest = ImageRequest.Builder(platformContext)
    .data(FileSystem.SYSTEM_TEMPORARY_DIRECTORY / "image_cache"/ "$host.png")
    .memoryCacheKey(host)
    .diskCacheKey(host)
    .diskCachePolicy(CachePolicy.ENABLED)
    .memoryCachePolicy(CachePolicy.ENABLED)
    .build()

  SubcomposeAsyncImage(
    model = pageIconRequest,
    contentDescription = null,
    loading = {
      Image(
        id = Res.drawable.ic_placeholder_icon,
        colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.background),
        modifier = Modifier
      )
    },
    error = {
      Image(
        id = Res.drawable.ic_placeholder_icon,
        colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.background),
        modifier = Modifier
      )
    },
    modifier = modifier
      .size(32.dp)
      .clip(shape = MaterialTheme.shapes.medium)
      .background(color = MaterialTheme.colorScheme.onBackground)
      .padding(all = 4.dp)
  )
}