package com.next.level.solutions.calculator.fb.mp.ui.screen.calculator.composable

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import kotlin.math.min

@Composable
fun TextAutoSize(
  text: String,
  modifier: Modifier = Modifier,
  style: TextStyle = LocalTextStyle.current,
) {
  var scaledTextStyle by remember(text) { mutableStateOf(style) }
  var readyToDraw by remember(text) { mutableStateOf(false) }

  Text(
    text = when {
      text.isEmpty() -> "0"
      else -> text
    },
    style = scaledTextStyle,
    softWrap = false,
    onTextLayout = { textLayoutResult ->
      when (textLayoutResult.size.width < textLayoutResult.multiParagraph.width) {
        true -> {
          val widthScale = textLayoutResult.size.width / textLayoutResult.multiParagraph.width
          val heightScale = textLayoutResult.size.height / textLayoutResult.multiParagraph.height
          val scale = min(widthScale, heightScale)

          scaledTextStyle = scaledTextStyle.copy(
            fontSize = scaledTextStyle.fontSize * scale,
          )
        }

        else -> readyToDraw = true
      }
    },
    modifier = modifier
      .height(height = style.lineHeight.value.dp)
      .wrapContentHeight()
      .drawWithContent { if (readyToDraw) drawContent() }
      .graphicsLayer { alpha = if (text.isEmpty()) 0.3f else 1f }
  )
}
