package com.next.level.solutions.calculator.fb.mp.ui.composable.action.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.adamglin.composeshadow.dropShadow
import com.next.level.solutions.calculator.fb.mp.ui.composable.image.Image
import com.next.level.solutions.calculator.fb.mp.ui.theme.TextStyleFactory

@Composable
fun ActionButton(
  modifier: Modifier = Modifier,
  textModifier: Modifier = Modifier,
  text: String? = "ActionButton",
  iconStart: ImageVector? = null,
  iconTop: ImageVector? = null,
  iconEnd: ImageVector? = null,
  iconBottom: ImageVector? = null,
  enabled: Boolean = true,
  style: TextStyle = TextStyleFactory.FS20.w600(),
  colors: ButtonColors = ButtonColors.default(),
  shape: Shape = MaterialTheme.shapes.large,
  iconStartSize: IconSize = IconSize(size = 24.dp),
  iconTopSize: IconSize = IconSize(size = 24.dp),
  iconEndSize: IconSize = IconSize(size = 24.dp),
  iconBottomSize: IconSize = IconSize(size = 24.dp),
  iconType: IconType = IconType.Icon,
  paddingValues: PaddingValues = PaddingValues(vertical = 20.dp, horizontal = 32.dp),
  contentSpace: ContentSpace = ContentSpace(vertical = 16.dp, horizontal = 8.dp),
  action: () -> Unit,
) {
  Content(
    modifier = modifier,
    textModifier = textModifier,
    text = text,
    iconStart = iconStart,
    iconTop = iconTop,
    iconEnd = iconEnd,
    iconBottom = iconBottom,
    enabled = enabled,
    style = style,
    colors = colors,
    shape = shape,
    iconStartSize = iconStartSize,
    iconTopSize = iconTopSize,
    iconEndSize = iconEndSize,
    iconBottomSize = iconBottomSize,
    iconType = iconType,
    paddingValues = paddingValues,
    contentSpace = contentSpace,
    action = action,
  )
}

@Composable
fun ActionButtonPreview(
  modifier: Modifier = Modifier,
  textModifier: Modifier = Modifier,
  text: String? = "ActionButton",
  iconStart: ImageVector? = null,
  iconTop: ImageVector? = null,
  iconEnd: ImageVector? = null,
  iconBottom: ImageVector? = null,
  enabled: Boolean = true,
  style: TextStyle = TextStyleFactory.FS20.w600(),
  colors: ButtonColors = ButtonColors.default(),
  shape: Shape = RoundedCornerShape(size = 16.dp),
  iconStartSize: IconSize = IconSize(size = 24.dp),
  iconTopSize: IconSize = IconSize(size = 24.dp),
  iconEndSize: IconSize = IconSize(size = 24.dp),
  iconBottomSize: IconSize = IconSize(size = 24.dp),
  iconType: IconType = IconType.Icon,
  paddingValues: PaddingValues = PaddingValues(vertical = 20.dp, horizontal = 32.dp),
  contentSpace: ContentSpace = ContentSpace(vertical = 16.dp, horizontal = 8.dp),
) {
  Content(
    modifier = modifier,
    textModifier = textModifier,
    text = text,
    iconStart = iconStart,
    iconTop = iconTop,
    iconEnd = iconEnd,
    iconBottom = iconBottom,
    enabled = enabled,
    style = style,
    colors = colors,
    shape = shape,
    iconStartSize = iconStartSize,
    iconTopSize = iconTopSize,
    iconEndSize = iconEndSize,
    iconBottomSize = iconBottomSize,
    iconType = iconType,
    paddingValues = paddingValues,
    contentSpace = contentSpace,
  )
}

@Composable
private fun Content(
  modifier: Modifier = Modifier,
  textModifier: Modifier = Modifier,
  text: String? = "ActionButton",
  iconStart: ImageVector? = null,
  iconTop: ImageVector? = null,
  iconEnd: ImageVector? = null,
  iconBottom: ImageVector? = null,
  enabled: Boolean = true,
  style: TextStyle = TextStyleFactory.FS20.w600(),
  colors: ButtonColors = ButtonColors.default(),
  shape: Shape = MaterialTheme.shapes.large,
  iconStartSize: IconSize = IconSize(size = 24.dp),
  iconTopSize: IconSize = IconSize(size = 24.dp),
  iconEndSize: IconSize = IconSize(size = 24.dp),
  iconBottomSize: IconSize = IconSize(size = 24.dp),
  iconType: IconType = IconType.Icon,
  paddingValues: PaddingValues = PaddingValues(vertical = 20.dp, horizontal = 32.dp),
  contentSpace: ContentSpace = ContentSpace(vertical = 16.dp, horizontal = 8.dp),
  action: () -> Unit = {},
) {
  val colorsValue: ButtonColors = remember(key1 = colors.toString()) { colors }
  val contentSpaceValue: ContentSpace = remember(key1 = contentSpace.toString()) { contentSpace }

  val iconTopSizeValue: IconSize = remember(key1 = iconTopSize.toString()) { iconTopSize }
  val iconBottomSizeValue: IconSize = remember(key1 = iconBottomSize.toString()) { iconBottomSize }
  val iconStartSizeValue: IconSize = remember(key1 = iconStartSize.toString()) { iconStartSize }
  val iconEndSizeValue: IconSize = remember(key1 = iconEndSize.toString()) { iconEndSize }

  val textM = remember(key1 = enabled) {
    modifier
      .dropShadow(
        shape = shape,
        offsetX = 3.dp,
        offsetY = 3.dp,
      )
      .clip(shape = shape)
      .background(
        color = when (enabled) {
          true -> colorsValue.containerColor
          else -> colorsValue.disabledContainerColor
        }
      )
      .clickable(enabled = enabled, onClick = action)
      .padding(paddingValues = paddingValues)
      .wrapContentWidth()
  }

  val icons = listOfNotNull(iconStart, iconTop, iconEnd, iconBottom)

  val nothing = icons.isEmpty() && text == null

  val onlyText = icons.isEmpty() && text != null
  val onlyIcon = icons.size == 1 && text == null

  val raw = iconStart != null || iconEnd != null
  val column = iconTop != null || iconBottom != null

  when {
    nothing -> {}

    onlyText -> text?.Draw(
      style = style,
      color = when (enabled) {
        true -> colorsValue.contentColor
        else -> colorsValue.disabledContentColor
      },
      modifier = textM
        .then(textModifier)
    )

    onlyIcon -> iconStart?.Draw(
      type = iconType,
      tint = when (enabled) {
        true -> colorsValue.contentColor
        else -> colorsValue.disabledContentColor
      },
      modifier = textM.size(size = iconStartSizeValue)
    ) ?: iconTop?.Draw(
      type = iconType,
      tint = when (enabled) {
        true -> colorsValue.contentColor
        else -> colorsValue.disabledContentColor
      },
      modifier = textM.size(size = iconTopSizeValue)
    ) ?: iconEnd?.Draw(
      type = iconType,
      tint = when (enabled) {
        true -> colorsValue.contentColor
        else -> colorsValue.disabledContentColor
      },
      modifier = textM.size(size = iconEndSizeValue)
    ) ?: iconBottom?.Draw(
      type = iconType,
      tint = when (enabled) {
        true -> colorsValue.contentColor
        else -> colorsValue.disabledContentColor
      },
      modifier = textM.size(size = iconBottomSizeValue)
    )

    raw && column -> All(
      text = text,
      iconStart = iconStart,
      iconTop = iconTop,
      iconEnd = iconEnd,
      iconBottom = iconBottom,
      style = style,
      iconStartSize = iconStartSizeValue,
      iconTopSize = iconTopSizeValue,
      iconEndSize = iconEndSizeValue,
      iconBottomSize = iconBottomSizeValue,
      iconType = iconType,
      contentSpace = contentSpaceValue,
      modifier = textM,
      textModifier = textModifier,
      color = when (enabled) {
        true -> colorsValue.contentColor
        else -> colorsValue.disabledContentColor
      },
    )

    column -> Column(
      text = text,
      iconTop = iconTop,
      iconBottom = iconBottom,
      style = style,
      iconTopSize = iconTopSizeValue,
      iconBottomSize = iconBottomSizeValue,
      iconType = iconType,
      contentSpace = contentSpaceValue.vertical,
      modifier = textM,
      textModifier = textModifier,
      color = when (enabled) {
        true -> colorsValue.contentColor
        else -> colorsValue.disabledContentColor
      },
    )

    raw -> Row(
      text = text,
      iconStart = iconStart,
      iconEnd = iconEnd,
      style = style,
      iconStartSize = iconStartSizeValue,
      iconEndSize = iconEndSizeValue,
      iconType = iconType,
      contentSpace = contentSpaceValue.horizontal,
      modifier = textM,
      textModifier = textModifier,
      color = when (enabled) {
        true -> colorsValue.contentColor
        else -> colorsValue.disabledContentColor
      },
    )
  }
}

@Composable
private fun ImageVector.Draw(
  type: IconType,
  tint: Color,
  modifier: Modifier = Modifier,
) {
  when (type) {
    IconType.Icon -> {
      Image(
        vector = this,
        colorFilter = tint,
        modifier = modifier
      )
    }

    IconType.Image -> {
      Image(
        vector = this,
        modifier = modifier
      )
    }
  }
}

@Composable
private fun String.Draw(
  color: Color,
  style: TextStyle,
  modifier: Modifier = Modifier,
) {
  Text(
    text = this,
    style = style,
    maxLines = 1,
    modifier = modifier,
    color = color,
  )
}

@Composable
private fun Row(
  text: String?,
  iconStart: ImageVector?,
  iconEnd: ImageVector?,
  style: TextStyle,
  iconStartSize: IconSize,
  iconEndSize: IconSize,
  iconType: IconType,
  color: Color,
  contentSpace: Dp,
  modifier: Modifier = Modifier,
  textModifier: Modifier = Modifier,
) {
  Row(
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically,
    modifier = modifier
  ) {
    iconStart?.let {
      iconStart.Draw(
        type = iconType,
        tint = color,
        modifier = Modifier.size(size = iconStartSize)
      )

      Spacer(modifier = Modifier.width(width = contentSpace))
    }

    text?.Draw(
      style = style,
      color = color,
      modifier = textModifier,
    )

    iconEnd?.let {
      Spacer(modifier = Modifier.width(width = contentSpace))

      iconEnd.Draw(
        type = iconType,
        tint = color,
        modifier = Modifier.size(size = iconEndSize)
      )
    }
  }
}

@Composable
private fun Column(
  text: String?,
  iconTop: ImageVector?,
  iconBottom: ImageVector?,
  style: TextStyle,
  iconTopSize: IconSize,
  iconBottomSize: IconSize,
  iconType: IconType,
  color: Color,
  contentSpace: Dp,
  modifier: Modifier = Modifier,
  textModifier: Modifier = Modifier,
) {
  Column(
    verticalArrangement = Arrangement.Top,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier
  ) {
    iconTop?.let {
      iconTop.Draw(
        type = iconType,
        tint = color,
        modifier = Modifier.size(size = iconTopSize)
      )

      Spacer(modifier = Modifier.height(height = contentSpace))
    }

    text?.Draw(
      style = style,
      color = color,
      modifier = textModifier,
    )

    iconBottom?.let {
      Spacer(modifier = Modifier.height(height = contentSpace))

      iconBottom.Draw(
        type = iconType,
        tint = color,
        modifier = Modifier.size(size = iconBottomSize)
      )
    }
  }
}

@Composable
private fun All(
  text: String?,
  iconStart: ImageVector?,
  iconTop: ImageVector?,
  iconEnd: ImageVector?,
  iconBottom: ImageVector?,
  style: TextStyle,
  iconStartSize: IconSize,
  iconTopSize: IconSize,
  iconEndSize: IconSize,
  iconBottomSize: IconSize,
  iconType: IconType,
  color: Color,
  contentSpace: ContentSpace,
  modifier: Modifier = Modifier,
  textModifier: Modifier = Modifier,
) {
  Column(
    verticalArrangement = Arrangement.Top,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier
  ) {
    iconTop?.let {
      iconTop.Draw(
        type = iconType,
        tint = color,
        modifier = Modifier.size(size = iconTopSize)
      )

      Spacer(modifier = Modifier.height(height = contentSpace.vertical))
    }

    Row(
      horizontalArrangement = Arrangement.Center,
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier
    ) {
      iconStart?.let {
        iconStart.Draw(
          type = iconType,
          tint = color,
          modifier = Modifier.size(size = iconStartSize)
        )

        Spacer(modifier = Modifier.width(width = contentSpace.horizontal))
      }

      text?.Draw(
        style = style,
        color = color,
        modifier = textModifier,
      )

      iconEnd?.let {
        Spacer(modifier = Modifier.width(width = contentSpace.horizontal))

        iconEnd.Draw(
          type = iconType,
          tint = color,
          modifier = Modifier.size(size = iconEndSize)
        )
      }
    }

    iconBottom?.let {
      Spacer(modifier = Modifier.height(height = contentSpace.vertical))

      iconBottom.Draw(
        type = iconType,
        tint = color,
        modifier = Modifier.size(size = iconBottomSize)
      )
    }
  }
}

@Stable
fun Modifier.size(size: IconSize): Modifier {
  return when {
    size.width == 0.dp || size.height == 0.dp -> this
    else -> size(size.width, size.height)
  }
}

@Stable
enum class IconType { Icon, Image }

@Stable
data class IconSize(
  val height: Dp = 24.dp,
  val width: Dp = 24.dp,
) {
  constructor(size: Dp) : this(height = size, width = size)
}

@Stable
data class ContentSpace(
  val horizontal: Dp = 8.dp,
  val vertical: Dp = 16.dp,
)

@Stable
data class ButtonColors(
  val contentColor: Color,
  val containerColor: Color,
  val disabledContentColor: Color,
  val disabledContainerColor: Color,
) {
  companion object {
    @Composable
    fun default(
      contentColor: Color = MaterialTheme.colorScheme.onPrimary,
      containerColor: Color = MaterialTheme.colorScheme.primary,
      disabledContentColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
      disabledContainerColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    ): ButtonColors = ButtonColors(
      contentColor = contentColor,
      containerColor = containerColor,
      disabledContentColor = disabledContentColor,
      disabledContainerColor = disabledContainerColor,
    )
  }
}