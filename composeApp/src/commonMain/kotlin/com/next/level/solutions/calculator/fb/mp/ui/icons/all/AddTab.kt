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
val MagicIcons.All.AddTab: ImageVector
  get() {
    if (_AddTab != null) {
      return _AddTab!!
    }
    _AddTab = ImageVector.Builder(
      name = "All.AddTab",
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
          moveTo(11f, 11f)
          verticalLineTo(5f)
          horizontalLineTo(13f)
          verticalLineTo(11f)
          horizontalLineTo(19f)
          verticalLineTo(13f)
          horizontalLineTo(13f)
          verticalLineTo(19f)
          horizontalLineTo(11f)
          verticalLineTo(13f)
          horizontalLineTo(5f)
          verticalLineTo(11f)
          horizontalLineTo(11f)
          close()
        }
      }
    }.build()

    return _AddTab!!
  }

@Suppress("ObjectPropertyName")
private var _AddTab: ImageVector? = null
