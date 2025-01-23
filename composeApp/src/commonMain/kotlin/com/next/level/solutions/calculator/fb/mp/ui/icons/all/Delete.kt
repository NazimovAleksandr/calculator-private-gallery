package com.next.level.solutions.calculator.fb.mp.ui.icons.all

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons

@Suppress("UnusedReceiverParameter")
val MagicIcons.All.Delete: ImageVector
  get() {
    if (_Delete != null) {
      return _Delete!!
    }
    _Delete = ImageVector.Builder(
      name = "All.Delete",
      defaultWidth = 24.dp,
      defaultHeight = 24.dp,
      viewportWidth = 24f,
      viewportHeight = 24f
    ).apply {
      path(fill = SolidColor(Color(0xFFFFFFFF))) {
        moveTo(17f, 6f)
        horizontalLineTo(22f)
        verticalLineTo(8f)
        horizontalLineTo(20f)
        verticalLineTo(21f)
        curveTo(20f, 21.265f, 19.895f, 21.52f, 19.707f, 21.707f)
        curveTo(19.52f, 21.895f, 19.265f, 22f, 19f, 22f)
        horizontalLineTo(5f)
        curveTo(4.735f, 22f, 4.48f, 21.895f, 4.293f, 21.707f)
        curveTo(4.105f, 21.52f, 4f, 21.265f, 4f, 21f)
        verticalLineTo(8f)
        horizontalLineTo(2f)
        verticalLineTo(6f)
        horizontalLineTo(7f)
        verticalLineTo(3f)
        curveTo(7f, 2.735f, 7.105f, 2.48f, 7.293f, 2.293f)
        curveTo(7.48f, 2.105f, 7.735f, 2f, 8f, 2f)
        horizontalLineTo(16f)
        curveTo(16.265f, 2f, 16.52f, 2.105f, 16.707f, 2.293f)
        curveTo(16.895f, 2.48f, 17f, 2.735f, 17f, 3f)
        verticalLineTo(6f)
        close()
        moveTo(18f, 8f)
        horizontalLineTo(6f)
        verticalLineTo(20f)
        horizontalLineTo(18f)
        verticalLineTo(8f)
        close()
        moveTo(9f, 4f)
        verticalLineTo(6f)
        horizontalLineTo(15f)
        verticalLineTo(4f)
        horizontalLineTo(9f)
        close()
      }
    }.build()

    return _Delete!!
  }

@Suppress("ObjectPropertyName")
private var _Delete: ImageVector? = null
