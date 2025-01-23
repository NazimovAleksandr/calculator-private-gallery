package com.next.level.solutions.calculator.fb.mp.ui.icons.all

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons

@Suppress("UnusedReceiverParameter")
val MagicIcons.All.Lock: ImageVector
  get() {
    if (_Lock != null) {
      return _Lock!!
    }
    _Lock = ImageVector.Builder(
      name = "All.Lock",
      defaultWidth = 32.dp,
      defaultHeight = 32.dp,
      viewportWidth = 32f,
      viewportHeight = 32f
    ).apply {
      path(fill = SolidColor(Color(0xFFF09200))) {
        moveTo(24f, 10.667f)
        horizontalLineTo(26.667f)
        curveTo(27.02f, 10.667f, 27.359f, 10.807f, 27.61f, 11.057f)
        curveTo(27.86f, 11.307f, 28f, 11.646f, 28f, 12f)
        verticalLineTo(28f)
        curveTo(28f, 28.354f, 27.86f, 28.693f, 27.61f, 28.943f)
        curveTo(27.359f, 29.193f, 27.02f, 29.333f, 26.667f, 29.333f)
        horizontalLineTo(5.333f)
        curveTo(4.98f, 29.333f, 4.641f, 29.193f, 4.391f, 28.943f)
        curveTo(4.14f, 28.693f, 4f, 28.354f, 4f, 28f)
        verticalLineTo(12f)
        curveTo(4f, 11.646f, 4.14f, 11.307f, 4.391f, 11.057f)
        curveTo(4.641f, 10.807f, 4.98f, 10.667f, 5.333f, 10.667f)
        horizontalLineTo(8f)
        verticalLineTo(9.333f)
        curveTo(8f, 7.212f, 8.843f, 5.177f, 10.343f, 3.676f)
        curveTo(11.843f, 2.176f, 13.878f, 1.333f, 16f, 1.333f)
        curveTo(18.122f, 1.333f, 20.157f, 2.176f, 21.657f, 3.676f)
        curveTo(23.157f, 5.177f, 24f, 7.212f, 24f, 9.333f)
        verticalLineTo(10.667f)
        close()
        moveTo(6.667f, 13.333f)
        verticalLineTo(26.667f)
        horizontalLineTo(25.333f)
        verticalLineTo(13.333f)
        horizontalLineTo(6.667f)
        close()
        moveTo(14.667f, 18.667f)
        horizontalLineTo(17.333f)
        verticalLineTo(21.333f)
        horizontalLineTo(14.667f)
        verticalLineTo(18.667f)
        close()
        moveTo(9.333f, 18.667f)
        horizontalLineTo(12f)
        verticalLineTo(21.333f)
        horizontalLineTo(9.333f)
        verticalLineTo(18.667f)
        close()
        moveTo(20f, 18.667f)
        horizontalLineTo(22.667f)
        verticalLineTo(21.333f)
        horizontalLineTo(20f)
        verticalLineTo(18.667f)
        close()
        moveTo(21.333f, 10.667f)
        verticalLineTo(9.333f)
        curveTo(21.333f, 7.919f, 20.771f, 6.562f, 19.771f, 5.562f)
        curveTo(18.771f, 4.562f, 17.414f, 4f, 16f, 4f)
        curveTo(14.585f, 4f, 13.229f, 4.562f, 12.229f, 5.562f)
        curveTo(11.229f, 6.562f, 10.667f, 7.919f, 10.667f, 9.333f)
        verticalLineTo(10.667f)
        horizontalLineTo(21.333f)
        close()
      }
    }.build()

    return _Lock!!
  }

@Suppress("ObjectPropertyName")
private var _Lock: ImageVector? = null
