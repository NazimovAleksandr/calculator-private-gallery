package com.next.level.solutions.calculator.fb.mp.ui.icons.all

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons

@Suppress("UnusedReceiverParameter")
val MagicIcons.All.ArrowForward: ImageVector
  get() {
    if (_ArrowForward != null) {
      return _ArrowForward!!
    }
    _ArrowForward = ImageVector.Builder(
      name = "All.ArrowForward",
      defaultWidth = 48.dp,
      defaultHeight = 48.dp,
      viewportWidth = 48f,
      viewportHeight = 48f
    ).apply {
      group(
        translationX = 12f,
        translationY = 12f,
      ) {
        path(
          fill = SolidColor(Color(0xFFFFFFFF)),
          fillAlpha = 0.5f
        ) {
          moveTo(4f, 11f)
          horizontalLineTo(16.17f)
          lineTo(10.58f, 5.41f)
          lineTo(12f, 4f)
          lineTo(20f, 12f)
          lineTo(12f, 20f)
          lineTo(10.59f, 18.59f)
          lineTo(16.17f, 13f)
          horizontalLineTo(4f)
          verticalLineTo(11f)
          close()
        }
      }

    }.build()

    return _ArrowForward!!
  }

@Suppress("ObjectPropertyName")
private var _ArrowForward: ImageVector? = null
