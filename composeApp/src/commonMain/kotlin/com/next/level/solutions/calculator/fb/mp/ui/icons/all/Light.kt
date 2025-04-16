package com.next.level.solutions.calculator.fb.mp.ui.icons.all

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathData
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons

@Suppress("UnusedReceiverParameter")
val MagicIcons.All.Light: ImageVector
  get() {
    if (_Light != null) {
      return _Light!!
    }
    _Light = ImageVector.Builder(
      name = "All.Light",
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
          stroke = SolidColor(Color(0xFF3C6E71)),
          strokeLineWidth = 1.5f,
          strokeLineCap = StrokeCap.Round
        ) {
          moveTo(10f, 2.895f)
          verticalLineTo(1f)
          moveTo(17.105f, 10f)
          horizontalLineTo(19f)
          moveTo(15.024f, 15.024f)
          lineTo(16.364f, 16.364f)
          moveTo(10f, 19f)
          verticalLineTo(17.105f)
          moveTo(1f, 10f)
          horizontalLineTo(2.895f)
          moveTo(3.636f, 3.636f)
          lineTo(4.976f, 4.976f)
          moveTo(4.976f, 15.024f)
          lineTo(3.636f, 16.364f)
          moveTo(16.364f, 3.636f)
          lineTo(15.024f, 4.976f)
          moveTo(13.79f, 10f)
          curveTo(13.79f, 12.093f, 12.093f, 13.79f, 10f, 13.79f)
          curveTo(7.907f, 13.79f, 6.211f, 12.093f, 6.211f, 10f)
          curveTo(6.211f, 7.907f, 7.907f, 6.211f, 10f, 6.211f)
          curveTo(12.093f, 6.211f, 13.79f, 7.907f, 13.79f, 10f)
          close()
        }
      }
    }.build()

    return _Light!!
  }

@Suppress("ObjectPropertyName")
private var _Light: ImageVector? = null
