package com.next.level.solutions.calculator.fb.mp.ui.icons.all

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons
import kotlin.Suppress

@Suppress("UnusedReceiverParameter")
val MagicIcons.All.Tick: ImageVector
  get() {
    if (_Tick != null) {
      return _Tick!!
    }
    _Tick = ImageVector.Builder(
      name = "All.Tick",
      defaultWidth = 24.dp,
      defaultHeight = 24.dp,
      viewportWidth = 24f,
      viewportHeight = 24f
    ).apply {
      path(fill = SolidColor(Color(0xFFFFFFFF))) {
        moveTo(9f, 19f)
        lineTo(3f, 13.043f)
        lineTo(5.1f, 10.957f)
        lineTo(9f, 14.83f)
        lineTo(18.9f, 5f)
        lineTo(21f, 7.085f)
        lineTo(9f, 19f)
        close()
      }
    }.build()

    return _Tick!!
  }

@Suppress("ObjectPropertyName")
private var _Tick: ImageVector? = null
