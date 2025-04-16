package com.next.level.solutions.calculator.fb.mp.ui.icons.all

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathData
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons

@Suppress("UnusedReceiverParameter")
val MagicIcons.All.Share: ImageVector
  get() {
    if (_Share != null) {
      return _Share!!
    }
    _Share = ImageVector.Builder(
      name = "All.Share",
      defaultWidth = 20.dp,
      defaultHeight = 20.dp,
      viewportWidth = 20f,
      viewportHeight = 20f
    ).apply {
      group(
        clipPathData = PathData {
          moveTo(0f, 0f)
          horizontalLineToRelative(20f)
          verticalLineToRelative(20f)
          horizontalLineToRelative(-20f)
          close()
        }
      ) {
        path(
          stroke = SolidColor(Color(0xFFF09200)),
          strokeLineWidth = 1.5f,
          strokeLineCap = StrokeCap.Round
        ) {
          moveTo(9f, 1f)
          curveTo(4.759f, 1f, 3.635f, 1.007f, 2.318f, 2.324f)
          curveTo(1f, 3.642f, 1f, 5.762f, 1f, 10.003f)
          curveTo(1f, 14.244f, 1f, 16.365f, 2.318f, 17.682f)
          curveTo(3.635f, 19f, 5.755f, 19f, 9.996f, 19f)
          curveTo(14.237f, 19f, 16.358f, 19f, 17.675f, 17.682f)
          curveTo(18.993f, 16.365f, 19f, 15.241f, 19f, 11f)
        }
        path(
          stroke = SolidColor(Color(0xFFF09200)),
          strokeLineWidth = 1.5f,
          strokeLineCap = StrokeCap.Round,
          strokeLineJoin = StrokeJoin.Round
        ) {
          moveTo(9f, 11f)
          lineTo(18.5f, 1.5f)
          moveTo(18.5f, 1.5f)
          horizontalLineTo(14.5f)
          moveTo(18.5f, 1.5f)
          verticalLineTo(5.5f)
        }
      }
    }.build()

    return _Share!!
  }

@Suppress("ObjectPropertyName")
private var _Share: ImageVector? = null
