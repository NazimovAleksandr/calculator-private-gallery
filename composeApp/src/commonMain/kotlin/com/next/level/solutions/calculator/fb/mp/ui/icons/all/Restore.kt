package com.next.level.solutions.calculator.fb.mp.ui.icons.all

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons

@Suppress("UnusedReceiverParameter")
val MagicIcons.All.Restore: ImageVector
  get() {
    if (_Reload != null) {
      return _Reload!!
    }
    _Reload = ImageVector.Builder(
      name = "All.Reload",
      defaultWidth = 24.dp,
      defaultHeight = 24.dp,
      viewportWidth = 24f,
      viewportHeight = 24f
    ).apply {
      path(
        stroke = SolidColor(Color(0xFFFFFFFF)),
        strokeLineWidth = 2f,
        strokeLineCap = StrokeCap.Round
      ) {
        moveTo(4.545f, 15.5f)
        curveTo(5.757f, 18.714f, 8.862f, 21f, 12.5f, 21f)
        curveTo(17.194f, 21f, 21f, 17.194f, 21f, 12.5f)
        curveTo(21f, 7.806f, 17.194f, 4f, 12.5f, 4f)
        curveTo(9.597f, 4f, 7.033f, 5.456f, 5.5f, 7.677f)
        moveTo(8.657f, 11f)
        horizontalLineTo(3f)
        verticalLineTo(5.343f)
      }
    }.build()

    return _Reload!!
  }

@Suppress("ObjectPropertyName")
private var _Reload: ImageVector? = null
