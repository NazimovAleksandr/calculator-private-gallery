package com.next.level.solutions.calculator.fb.mp.ui.icons.all

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons
import kotlin.Suppress

@Suppress("UnusedReceiverParameter")
val MagicIcons.All.CheckOff: ImageVector
  get() {
    if (_CheckOff != null) {
      return _CheckOff!!
    }
    _CheckOff = ImageVector.Builder(
      name = "All.CheckOff",
      defaultWidth = 40.dp,
      defaultHeight = 40.dp,
      viewportWidth = 40f,
      viewportHeight = 40f
    ).apply {
      group {
        path(
          fill = SolidColor(Color(0xFFFFFFFF)),
          fillAlpha = 0.3f,
          stroke = SolidColor(Color(0xFFFFFFFF)),
          strokeLineWidth = 2f
        ) {
          moveTo(11f, 20f)
          curveTo(11f, 15.032f, 15.032f, 11f, 20f, 11f)
          curveTo(24.968f, 11f, 29f, 15.032f, 29f, 20f)
          curveTo(29f, 24.968f, 24.968f, 29f, 20f, 29f)
          curveTo(15.032f, 29f, 11f, 24.968f, 11f, 20f)
          close()
        }
      }
    }.build()

    return _CheckOff!!
  }

@Suppress("ObjectPropertyName")
private var _CheckOff: ImageVector? = null
