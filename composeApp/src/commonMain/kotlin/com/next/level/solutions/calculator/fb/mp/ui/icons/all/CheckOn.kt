package com.next.level.solutions.calculator.fb.mp.ui.icons.all

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons

@Suppress("UnusedReceiverParameter")
val MagicIcons.All.CheckOn: ImageVector
  get() {
    if (_CheckOn != null) {
      return _CheckOn!!
    }
    _CheckOn = ImageVector.Builder(
      name = "All.CheckOn",
      defaultWidth = 40.dp,
      defaultHeight = 40.dp,
      viewportWidth = 40f,
      viewportHeight = 40f
    ).apply {
      group {
        path(
          fill = SolidColor(Color(0xFFF09200)),
          stroke = SolidColor(Color(0xFFFFFFFF)),
          strokeLineWidth = 2f
        ) {
          moveTo(20f, 9f)
          curveTo(13.928f, 9f, 9f, 13.928f, 9f, 20f)
          curveTo(9f, 26.072f, 13.928f, 31f, 20f, 31f)
          curveTo(26.072f, 31f, 31f, 26.072f, 31f, 20f)
          curveTo(31f, 13.928f, 26.072f, 9f, 20f, 9f)
          close()
        }
        path(fill = SolidColor(Color(0xFFFFFFFF))) {
          moveTo(17.972f, 24.733f)
          lineTo(13.915f, 20.705f)
          lineTo(15.335f, 19.295f)
          lineTo(17.972f, 21.913f)
          lineTo(24.665f, 15.267f)
          lineTo(26.084f, 16.677f)
          lineTo(17.972f, 24.733f)
          close()
        }
      }
    }.build()

    return _CheckOn!!
  }

@Suppress("ObjectPropertyName")
private var _CheckOn: ImageVector? = null
