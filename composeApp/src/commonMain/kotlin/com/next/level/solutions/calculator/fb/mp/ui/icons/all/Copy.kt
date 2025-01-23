package com.next.level.solutions.calculator.fb.mp.ui.icons.all

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons

@Suppress("UnusedReceiverParameter")
val MagicIcons.All.Copy: ImageVector
  get() {
    if (_Copy != null) {
      return _Copy!!
    }
    _Copy = ImageVector.Builder(
      name = "All.Copy",
      defaultWidth = 24.dp,
      defaultHeight = 24.dp,
      viewportWidth = 24f,
      viewportHeight = 24f
    ).apply {
      path(
        stroke = SolidColor(Color(0xFF000000)),
        strokeLineWidth = 2f,
        strokeLineCap = StrokeCap.Round
      ) {
        moveTo(20f, 9f)
        horizontalLineTo(16f)
        curveTo(14.895f, 9f, 14f, 8.105f, 14f, 7f)
        verticalLineTo(3f)
        moveTo(20f, 8.828f)
        verticalLineTo(17f)
        curveTo(20f, 18.105f, 19.105f, 19f, 18f, 19f)
        horizontalLineTo(10f)
        curveTo(8.895f, 19f, 8f, 18.105f, 8f, 17f)
        verticalLineTo(5f)
        curveTo(8f, 3.895f, 8.895f, 3f, 10f, 3f)
        horizontalLineTo(14.172f)
        curveTo(14.702f, 3f, 15.211f, 3.211f, 15.586f, 3.586f)
        lineTo(19.414f, 7.414f)
        curveTo(19.789f, 7.789f, 20f, 8.298f, 20f, 8.828f)
        close()
      }
      path(
        stroke = SolidColor(Color(0xFF000000)),
        strokeLineWidth = 2f,
        strokeLineCap = StrokeCap.Round
      ) {
        moveTo(8f, 6f)
        horizontalLineTo(7f)
        curveTo(5.895f, 6f, 5f, 6.895f, 5f, 8f)
        verticalLineTo(20f)
        curveTo(5f, 21.105f, 5.895f, 22f, 7f, 22f)
        horizontalLineTo(15f)
        curveTo(16.105f, 22f, 17f, 21.105f, 17f, 20f)
        verticalLineTo(19f)
      }
    }.build()

    return _Copy!!
  }

@Suppress("ObjectPropertyName")
private var _Copy: ImageVector? = null
