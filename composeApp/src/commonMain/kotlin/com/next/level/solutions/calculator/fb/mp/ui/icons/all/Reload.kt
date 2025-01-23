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
val MagicIcons.All.Reload: ImageVector
  get() {
    if (_Reload != null) {
      return _Reload!!
    }
    _Reload = ImageVector.Builder(
      name = "All.Reload",
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
          moveTo(5.463f, 4.433f)
          curveTo(7.278f, 2.861f, 9.599f, 1.997f, 12f, 2f)
          curveTo(17.523f, 2f, 22f, 6.477f, 22f, 12f)
          curveTo(22f, 14.136f, 21.33f, 16.116f, 20.19f, 17.74f)
          lineTo(17f, 12f)
          horizontalLineTo(20f)
          curveTo(20f, 10.432f, 19.539f, 8.898f, 18.675f, 7.589f)
          curveTo(17.81f, 6.281f, 16.58f, 5.255f, 15.137f, 4.64f)
          curveTo(13.694f, 4.025f, 12.103f, 3.848f, 10.56f, 4.13f)
          curveTo(9.017f, 4.412f, 7.591f, 5.142f, 6.46f, 6.228f)
          lineTo(5.463f, 4.433f)
          close()
          moveTo(18.537f, 19.567f)
          curveTo(16.722f, 21.139f, 14.401f, 22.003f, 12f, 22f)
          curveTo(6.477f, 22f, 2f, 17.523f, 2f, 12f)
          curveTo(2f, 9.864f, 2.67f, 7.884f, 3.81f, 6.26f)
          lineTo(7f, 12f)
          horizontalLineTo(4f)
          curveTo(4f, 13.568f, 4.461f, 15.102f, 5.325f, 16.411f)
          curveTo(6.19f, 17.719f, 7.42f, 18.745f, 8.863f, 19.36f)
          curveTo(10.306f, 19.975f, 11.897f, 20.152f, 13.44f, 19.87f)
          curveTo(14.983f, 19.588f, 16.409f, 18.858f, 17.54f, 17.772f)
          lineTo(18.537f, 19.567f)
          close()
        }
      }
    }.build()

    return _Reload!!
  }

@Suppress("ObjectPropertyName")
private var _Reload: ImageVector? = null
