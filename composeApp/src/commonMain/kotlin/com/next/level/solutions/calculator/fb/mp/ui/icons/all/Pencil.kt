package com.next.level.solutions.calculator.fb.mp.ui.icons.all

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons

@Suppress("UnusedReceiverParameter")
val MagicIcons.All.Pencil: ImageVector
  get() {
    if (_Pencil != null) {
      return _Pencil!!
    }
    _Pencil = ImageVector.Builder(
      name = "All.Pencil",
      defaultWidth = 24.dp,
      defaultHeight = 24.dp,
      viewportWidth = 24f,
      viewportHeight = 24f
    ).apply {
      path(
        stroke = SolidColor(Color(0xFF000000)),
        strokeLineWidth = 2f,
        strokeLineCap = StrokeCap.Round
      ) {
        moveTo(15.148f, 5.175f)
        lineTo(18.845f, 8.833f)
        moveTo(19.237f, 3.914f)
        lineTo(20.086f, 4.763f)
        curveTo(20.867f, 5.544f, 20.867f, 6.811f, 20.086f, 7.592f)
        lineTo(8.85f, 18.827f)
        curveTo(8.631f, 19.047f, 8.363f, 19.212f, 8.069f, 19.311f)
        lineTo(3.949f, 20.684f)
        curveTo(3.558f, 20.814f, 3.186f, 20.442f, 3.317f, 20.051f)
        lineTo(4.69f, 15.932f)
        curveTo(4.788f, 15.637f, 4.953f, 15.37f, 5.173f, 15.15f)
        lineTo(16.409f, 3.914f)
        curveTo(17.19f, 3.133f, 18.456f, 3.133f, 19.237f, 3.914f)
        close()
      }
    }.build()

    return _Pencil!!
  }

@Suppress("ObjectPropertyName")
private var _Pencil: ImageVector? = null
