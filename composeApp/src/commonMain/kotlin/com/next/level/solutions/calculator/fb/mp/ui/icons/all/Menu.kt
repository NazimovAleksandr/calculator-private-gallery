package com.next.level.solutions.calculator.fb.mp.ui.icons.all

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons
import kotlin.Suppress

@Suppress("UnusedReceiverParameter")
val MagicIcons.All.Menu: ImageVector
  get() {
    if (_Menu != null) {
      return _Menu!!
    }
    _Menu = ImageVector.Builder(
      name = "All.Menu",
      defaultWidth = 24.dp,
      defaultHeight = 24.dp,
      viewportWidth = 24f,
      viewportHeight = 24f
    ).apply {
      path(
        fill = SolidColor(Color(0xFFFFFFFF)),
        pathFillType = PathFillType.EvenOdd
      ) {
        moveTo(12f, 8f)
        curveTo(13.1f, 8f, 14f, 7.1f, 14f, 6f)
        curveTo(14f, 4.9f, 13.1f, 4f, 12f, 4f)
        curveTo(10.9f, 4f, 10f, 4.9f, 10f, 6f)
        curveTo(10f, 7.1f, 10.9f, 8f, 12f, 8f)
        close()
        moveTo(12f, 10f)
        curveTo(10.9f, 10f, 10f, 10.9f, 10f, 12f)
        curveTo(10f, 13.1f, 10.9f, 14f, 12f, 14f)
        curveTo(13.1f, 14f, 14f, 13.1f, 14f, 12f)
        curveTo(14f, 10.9f, 13.1f, 10f, 12f, 10f)
        close()
        moveTo(10f, 18f)
        curveTo(10f, 16.9f, 10.9f, 16f, 12f, 16f)
        curveTo(13.1f, 16f, 14f, 16.9f, 14f, 18f)
        curveTo(14f, 19.1f, 13.1f, 20f, 12f, 20f)
        curveTo(10.9f, 20f, 10f, 19.1f, 10f, 18f)
        close()
      }
    }.build()

    return _Menu!!
  }

@Suppress("ObjectPropertyName")
private var _Menu: ImageVector? = null
