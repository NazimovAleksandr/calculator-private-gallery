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
val MagicIcons.All.File: ImageVector
  get() {
    if (_File != null) {
      return _File!!
    }
    _File = ImageVector.Builder(
      name = "All.File",
      defaultWidth = 37.dp,
      defaultHeight = 32.dp,
      viewportWidth = 37f,
      viewportHeight = 32f
    ).apply {
      path(
        fill = SolidColor(Color(0xFFF7F7F7)),
        stroke = SolidColor(Color(0xFFD1D1D1)),
        strokeLineWidth = 0.1f,
        strokeLineCap = StrokeCap.Round,
        strokeLineJoin = StrokeJoin.Round
      ) {
        moveTo(29.929f, 6.938f)
        verticalLineTo(31.47f)
        lineTo(29.93f, 31.471f)
        horizontalLineTo(7.53f)
        lineTo(7.529f, 31.47f)
        lineTo(7.53f, 0.54f)
        lineTo(23.529f, 0.538f)
        lineTo(29.929f, 6.938f)
        close()
      }
      path(fill = SolidColor(Color(0xFFEBEBEB))) {
        moveTo(29.929f, 6.938f)
        lineTo(24.067f, 6.933f)
        curveTo(23.529f, 6.933f, 23.534f, 6.933f, 23.529f, 6.4f)
        verticalLineTo(0.538f)
      }
      path(
        stroke = SolidColor(Color(0xFFD1D1D1)),
        strokeLineWidth = 0.1f,
        strokeLineCap = StrokeCap.Square,
        strokeLineJoin = StrokeJoin.Round
      ) {
        moveTo(29.929f, 6.938f)
        lineTo(24.067f, 6.933f)
        curveTo(23.529f, 6.933f, 23.534f, 6.933f, 23.529f, 6.4f)
        verticalLineTo(0.538f)
      }
    }.build()

    return _File!!
  }

@Suppress("ObjectPropertyName")
private var _File: ImageVector? = null
