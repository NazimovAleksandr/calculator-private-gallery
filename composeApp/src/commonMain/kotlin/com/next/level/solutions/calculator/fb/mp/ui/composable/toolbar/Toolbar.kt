package com.next.level.solutions.calculator.fb.mp.ui.composable.toolbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.next.level.solutions.calculator.fb.mp.ui.composable.image.Image
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.ArrowBack
import com.next.level.solutions.calculator.fb.mp.ui.theme.TextStyleFactory

@Composable
fun Toolbar(
  title: String,
  modifier: Modifier = Modifier,
  navIcon: ImageVector? = MagicIcons.All.ArrowBack,
  navAction: () -> Unit = {},
  menu: @Composable RowScope.() -> Unit = {},
) {
  Content(
    title = {
      Text(
        text = title,
        style = TextStyleFactory.FS22.w400(),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier
          .weight(weight = 1f)
      )
    },
    modifier = modifier,
    navIcon = navIcon,
    navAction = navAction,
    menu = menu,
  )
}

@Composable
fun Toolbar(
  title: @Composable RowScope.() -> Unit,
  modifier: Modifier = Modifier,
  navIcon: ImageVector? = MagicIcons.All.ArrowBack,
  navAction: () -> Unit = {},
  menu: @Composable RowScope.() -> Unit = {},
) {
  Content(
    title = title,
    modifier = modifier,
    navIcon = navIcon,
    navAction = navAction,
    menu = menu,
  )
}

@Composable
inline fun Content(
  title: @Composable RowScope.() -> Unit = {
    Text(
      text = "Title",
      style = TextStyleFactory.FS22.w400(),
      maxLines = 1,
      overflow = TextOverflow.Ellipsis,
      modifier = Modifier
        .weight(weight = 1f)
    )
  },
  modifier: Modifier = Modifier,
  navIcon: ImageVector? = MagicIcons.All.ArrowBack,
  noinline navAction: () -> Unit = {},
  crossinline menu: @Composable RowScope.() -> Unit = {},
) {
  Row(
    horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
    verticalAlignment = Alignment.CenterVertically,
    modifier = modifier
      .padding(vertical = 12.dp, horizontal = 8.dp)
      .fillMaxWidth()
  ) {
    Image(
      vector = navIcon,
      colorFilter = MaterialTheme.colorScheme.onBackground,
      modifier = Modifier
        .size(40.dp)
        .clip(shape = CircleShape)
        .clickable(onClick = navAction)
        .padding(all = 8.dp)
    )

    title.invoke(this)

    menu.invoke(this)
  }
}