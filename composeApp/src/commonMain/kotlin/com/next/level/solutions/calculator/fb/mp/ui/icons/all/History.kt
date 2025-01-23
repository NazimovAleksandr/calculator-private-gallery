package com.next.level.solutions.calculator.fb.mp.ui.icons.all

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathData
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons

@Suppress("UnusedReceiverParameter")
val MagicIcons.All.History: ImageVector
  get() {
    if (_History != null) {
      return _History!!
    }
    _History = ImageVector.Builder(
      name = "All.History",
      defaultWidth = 24.dp,
      defaultHeight = 24.dp,
      viewportWidth = 24f,
      viewportHeight = 24f
    ).apply {
      group(
        clipPathData = PathData {
          moveTo(0f, 0f)
          horizontalLineToRelative(24f)
          verticalLineToRelative(24f)
          horizontalLineToRelative(-24f)
          close()
        }
      ) {
        path(fill = SolidColor(Color(0xFFFFFFFF))) {
          moveTo(12f, 22f)
          curveTo(6.477f, 22f, 2f, 17.523f, 2f, 12f)
          curveTo(2f, 6.477f, 6.477f, 2f, 12f, 2f)
          curveTo(17.523f, 2f, 22f, 6.477f, 22f, 12f)
          curveTo(22f, 17.523f, 17.523f, 22f, 12f, 22f)
          close()
          moveTo(12f, 20f)
          curveTo(14.122f, 20f, 16.157f, 19.157f, 17.657f, 17.657f)
          curveTo(19.157f, 16.157f, 20f, 14.122f, 20f, 12f)
          curveTo(20f, 9.878f, 19.157f, 7.843f, 17.657f, 6.343f)
          curveTo(16.157f, 4.843f, 14.122f, 4f, 12f, 4f)
          curveTo(9.878f, 4f, 7.843f, 4.843f, 6.343f, 6.343f)
          curveTo(4.843f, 7.843f, 4f, 9.878f, 4f, 12f)
          curveTo(4f, 14.122f, 4.843f, 16.157f, 6.343f, 17.657f)
          curveTo(7.843f, 19.157f, 9.878f, 20f, 12f, 20f)
          close()
          moveTo(13f, 12f)
          horizontalLineTo(17f)
          verticalLineTo(14f)
          horizontalLineTo(11f)
          verticalLineTo(7f)
          horizontalLineTo(13f)
          verticalLineTo(12f)
          close()
        }
      }
    }.build()

    return _History!!
  }

@Suppress("ObjectPropertyName")
private var _History: ImageVector? = null
