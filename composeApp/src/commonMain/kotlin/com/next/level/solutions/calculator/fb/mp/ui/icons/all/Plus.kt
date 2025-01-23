package com.next.level.solutions.calculator.fb.mp.ui.icons.all

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons

@Suppress("UnusedReceiverParameter")
val MagicIcons.All.Plus: ImageVector
  get() {
    if (_Plus != null) {
      return _Plus!!
    }
    _Plus = ImageVector.Builder(
      name = "All.Plus",
      defaultWidth = 28.dp,
      defaultHeight = 28.dp,
      viewportWidth = 28f,
      viewportHeight = 28f
    ).apply {
      path(fill = SolidColor(Color(0xFFFFFFFF))) {
        moveTo(14f, 0.5f)
        lineTo(14f, 0.5f)
        arcTo(2.531f, 2.531f, 0f, isMoreThanHalf = false, isPositiveArc = true, 16.531f, 3.031f)
        lineTo(16.531f, 24.969f)
        arcTo(2.531f, 2.531f, 0f, isMoreThanHalf = false, isPositiveArc = true, 14f, 27.5f)
        lineTo(14f, 27.5f)
        arcTo(2.531f, 2.531f, 0f, isMoreThanHalf = false, isPositiveArc = true, 11.469f, 24.969f)
        lineTo(11.469f, 3.031f)
        arcTo(2.531f, 2.531f, 0f, isMoreThanHalf = false, isPositiveArc = true, 14f, 0.5f)
        close()
      }
      path(fill = SolidColor(Color(0xFFFFFFFF))) {
        moveTo(27.5f, 14f)
        lineTo(27.5f, 14f)
        arcTo(2.531f, 2.531f, 0f, isMoreThanHalf = false, isPositiveArc = true, 24.969f, 16.531f)
        lineTo(3.031f, 16.531f)
        arcTo(2.531f, 2.531f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.5f, 14f)
        lineTo(0.5f, 14f)
        arcTo(2.531f, 2.531f, 0f, isMoreThanHalf = false, isPositiveArc = true, 3.031f, 11.469f)
        lineTo(24.969f, 11.469f)
        arcTo(2.531f, 2.531f, 0f, isMoreThanHalf = false, isPositiveArc = true, 27.5f, 14f)
        close()
      }
    }.build()

    return _Plus!!
  }

@Suppress("ObjectPropertyName")
private var _Plus: ImageVector? = null
