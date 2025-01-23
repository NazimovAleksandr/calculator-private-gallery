package com.next.level.solutions.calculator.fb.mp.ui.icons.all

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons

@Suppress("UnusedReceiverParameter")
val MagicIcons.All.Close: ImageVector
  get() {
    if (_Close != null) {
      return _Close!!
    }
    _Close = ImageVector.Builder(
      name = "All.Close",
      defaultWidth = 16.dp,
      defaultHeight = 16.dp,
      viewportWidth = 16f,
      viewportHeight = 16f
    ).apply {
      path(
        fill = SolidColor(Color(0xFFFFFFFF)),
        fillAlpha = 1f,
        pathFillType = PathFillType.EvenOdd
      ) {
        moveTo(14.193f, 14.031f)
        curveTo(14.728f, 13.496f, 14.728f, 12.628f, 14.193f, 12.093f)
        lineTo(9.972f, 7.873f)
        lineTo(13.938f, 3.907f)
        curveTo(14.473f, 3.372f, 14.473f, 2.504f, 13.938f, 1.968f)
        curveTo(13.403f, 1.433f, 12.535f, 1.433f, 11.999f, 1.968f)
        lineTo(8.034f, 5.934f)
        lineTo(4.001f, 1.901f)
        curveTo(3.466f, 1.366f, 2.598f, 1.366f, 2.062f, 1.901f)
        curveTo(1.527f, 2.437f, 1.527f, 3.305f, 2.062f, 3.84f)
        lineTo(6.095f, 7.873f)
        lineTo(1.808f, 12.16f)
        curveTo(1.272f, 12.695f, 1.272f, 13.563f, 1.808f, 14.099f)
        curveTo(2.343f, 14.634f, 3.211f, 14.634f, 3.746f, 14.099f)
        lineTo(8.034f, 9.811f)
        lineTo(12.254f, 14.031f)
        curveTo(12.789f, 14.567f, 13.657f, 14.567f, 14.193f, 14.031f)
        close()
      }
    }.build()

    return _Close!!
  }

@Suppress("ObjectPropertyName")
private var _Close: ImageVector? = null
