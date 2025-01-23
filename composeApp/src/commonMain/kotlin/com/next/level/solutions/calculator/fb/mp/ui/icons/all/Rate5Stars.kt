package com.next.level.solutions.calculator.fb.mp.ui.icons.all

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons

@Suppress("UnusedReceiverParameter")
val MagicIcons.All.Rate5Stars: ImageVector
  get() {
    if (_Rate5Stars != null) {
      return _Rate5Stars!!
    }
    _Rate5Stars = ImageVector.Builder(
      name = "All.Rate5Stars",
      defaultWidth = 22.dp,
      defaultHeight = 22.dp,
      viewportWidth = 22f,
      viewportHeight = 22f
    ).apply {
      path(
        stroke = SolidColor(Color(0xFFF09200)),
        strokeLineWidth = 1.5f,
        strokeLineCap = StrokeCap.Round,
        strokeLineJoin = StrokeJoin.Round
      ) {
        moveTo(12.728f, 2.444f)
        lineTo(14.487f, 5.993f)
        curveTo(14.727f, 6.487f, 15.367f, 6.961f, 15.907f, 7.051f)
        lineTo(19.097f, 7.586f)
        curveTo(21.137f, 7.929f, 21.617f, 9.421f, 20.147f, 10.892f)
        lineTo(17.667f, 13.393f)
        curveTo(17.247f, 13.816f, 17.017f, 14.633f, 17.147f, 15.217f)
        lineTo(17.857f, 18.313f)
        curveTo(18.417f, 20.762f, 17.127f, 21.71f, 14.977f, 20.43f)
        lineTo(11.988f, 18.645f)
        curveTo(11.448f, 18.323f, 10.558f, 18.323f, 10.008f, 18.645f)
        lineTo(7.018f, 20.43f)
        curveTo(4.878f, 21.71f, 3.579f, 20.752f, 4.139f, 18.313f)
        lineTo(4.849f, 15.217f)
        curveTo(4.978f, 14.633f, 4.749f, 13.816f, 4.329f, 13.393f)
        lineTo(1.849f, 10.892f)
        curveTo(0.389f, 9.421f, 0.859f, 7.929f, 2.899f, 7.586f)
        lineTo(6.088f, 7.051f)
        curveTo(6.618f, 6.961f, 7.258f, 6.487f, 7.498f, 5.993f)
        lineTo(9.258f, 2.444f)
        curveTo(10.218f, 0.519f, 11.778f, 0.519f, 12.728f, 2.444f)
        close()
      }
    }.build()

    return _Rate5Stars!!
  }

@Suppress("ObjectPropertyName")
private var _Rate5Stars: ImageVector? = null
