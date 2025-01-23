package com.next.level.solutions.calculator.fb.mp.ui.icons.all

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons

@Suppress("UnusedReceiverParameter")
val MagicIcons.All.Home: ImageVector
  get() {
    if (_Home != null) {
      return _Home!!
    }
    _Home = ImageVector.Builder(
      name = "All.Home",
      defaultWidth = 24.dp,
      defaultHeight = 24.dp,
      viewportWidth = 24f,
      viewportHeight = 24f
    ).apply {
      path(
        fill = SolidColor(Color(0xFFFFFFFF)),
      ) {
        moveTo(13f, 19f)
        horizontalLineTo(19f)
        verticalLineTo(9.978f)
        lineTo(12f, 4.534f)
        lineTo(5f, 9.978f)
        verticalLineTo(19f)
        horizontalLineTo(11f)
        verticalLineTo(13f)
        horizontalLineTo(13f)
        verticalLineTo(19f)
        close()
        moveTo(21f, 20f)
        curveTo(21f, 20.265f, 20.895f, 20.52f, 20.707f, 20.707f)
        curveTo(20.52f, 20.895f, 20.265f, 21f, 20f, 21f)
        horizontalLineTo(4f)
        curveTo(3.735f, 21f, 3.48f, 20.895f, 3.293f, 20.707f)
        curveTo(3.105f, 20.52f, 3f, 20.265f, 3f, 20f)
        verticalLineTo(9.49f)
        curveTo(3f, 9.338f, 3.035f, 9.187f, 3.102f, 9.05f)
        curveTo(3.168f, 8.913f, 3.266f, 8.794f, 3.386f, 8.7f)
        lineTo(11.386f, 2.478f)
        curveTo(11.561f, 2.341f, 11.778f, 2.267f, 12f, 2.267f)
        curveTo(12.222f, 2.267f, 12.439f, 2.341f, 12.614f, 2.478f)
        lineTo(20.614f, 8.7f)
        curveTo(20.734f, 8.794f, 20.832f, 8.913f, 20.899f, 9.05f)
        curveTo(20.965f, 9.187f, 21f, 9.338f, 21f, 9.49f)
        verticalLineTo(20f)
        close()
      }
    }.build()

    return _Home!!
  }

@Suppress("ObjectPropertyName")
private var _Home: ImageVector? = null
