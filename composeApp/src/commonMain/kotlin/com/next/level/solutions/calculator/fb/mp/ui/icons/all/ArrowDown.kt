package com.next.level.solutions.calculator.fb.mp.ui.icons.all

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons
import kotlin.Suppress

@Suppress("UnusedReceiverParameter")
val MagicIcons.All.ArrowDown: ImageVector
  get() {
    if (_ArrowDown != null) {
      return _ArrowDown!!
    }
    _ArrowDown = ImageVector.Builder(
      name = "All.ArrowDown",
      defaultWidth = 24.dp,
      defaultHeight = 24.dp,
      viewportWidth = 24f,
      viewportHeight = 24f
    ).apply {
      path(
        stroke = SolidColor(Color(0xFFFFFFFF)),
        strokeLineWidth = 2f
      ) {
        moveTo(6f, 9f)
        lineTo(12f, 15f)
        lineTo(18f, 9f)
      }
    }.build()

    return _ArrowDown!!
  }

@Suppress("ObjectPropertyName")
private var _ArrowDown: ImageVector? = null
