package com.next.level.solutions.calculator.fb.mp.ui.icons.all

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons

@Suppress("UnusedReceiverParameter")
val MagicIcons.All.Dark: ImageVector
  get() {
    if (_Dark != null) {
      return _Dark!!
    }
    _Dark = ImageVector.Builder(
      name = "All.Dark",
      defaultWidth = 18.dp,
      defaultHeight = 18.dp,
      viewportWidth = 18f,
      viewportHeight = 18f
    ).apply {
      path(fill = SolidColor(Color(0xFFFAFAFA))) {
        moveTo(8.989f, 0.465f)
        curveTo(7.408f, 0.022f, 5.874f, 0.155f, 4.517f, 0.713f)
        curveTo(3.959f, 0.943f, 3.812f, 1.793f, 4.277f, 2.227f)
        curveTo(5.163f, 3.054f, 5.876f, 4.096f, 6.365f, 5.278f)
        curveTo(6.854f, 6.459f, 7.108f, 7.75f, 7.106f, 9.057f)
        curveTo(7.108f, 10.364f, 6.854f, 11.655f, 6.365f, 12.836f)
        curveTo(5.876f, 14.017f, 5.163f, 15.059f, 4.277f, 15.886f)
        curveTo(3.82f, 16.32f, 3.952f, 17.17f, 4.517f, 17.392f)
        curveTo(5.323f, 17.728f, 6.199f, 17.914f, 7.106f, 17.914f)
        curveTo(11.795f, 17.914f, 15.515f, 13.149f, 14.756f, 7.639f)
        curveTo(14.283f, 4.167f, 11.973f, 1.297f, 8.989f, 0.465f)
        verticalLineTo(0.465f)
        close()
      }
    }.build()

    return _Dark!!
  }

@Suppress("ObjectPropertyName")
private var _Dark: ImageVector? = null
