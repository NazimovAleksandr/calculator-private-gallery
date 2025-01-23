package com.next.level.solutions.calculator.fb.mp.ui.icons.all

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons

@Suppress("UnusedReceiverParameter")
val MagicIcons.All.Image: ImageVector
  get() {
    if (_Image != null) {
      return _Image!!
    }
    _Image = ImageVector.Builder(
      name = "All.Image",
      defaultWidth = 32.dp,
      defaultHeight = 32.dp,
      viewportWidth = 32f,
      viewportHeight = 32f
    ).apply {
      path(fill = SolidColor(Color(0xFFFFFFFF))) {
        moveTo(4.667f, 32f)
        horizontalLineTo(27.524f)
        verticalLineTo(6.4f)
        lineTo(21.353f, 0f)
        horizontalLineTo(4.667f)
        verticalLineTo(32f)
        close()
      }
      path(
        fill = Brush.radialGradient(
          colorStops = arrayOf(
            0f to Color(0xFFBD8AF5),
            0.137f to Color(0xFFB88BF5),
            0.309f to Color(0xFFA88FF3),
            0.499f to Color(0xFF8F96F2),
            0.702f to Color(0xFF6B9EEF),
            0.913f to Color(0xFF3EAAEC),
            1f to Color(0xFF29AFEA)
          ),
          center = Offset(27.907f, 23.391f),
          radius = 11.07f
        )
      ) {
        moveTo(23.816f, 8.686f)
        horizontalLineTo(8.375f)
        curveTo(7.842f, 8.686f, 7.41f, 9.118f, 7.41f, 9.651f)
        verticalLineTo(25.092f)
        curveTo(7.41f, 25.625f, 7.842f, 26.057f, 8.375f, 26.057f)
        horizontalLineTo(23.816f)
        curveTo(24.35f, 26.057f, 24.782f, 25.625f, 24.782f, 25.092f)
        verticalLineTo(9.651f)
        curveTo(24.782f, 9.118f, 24.35f, 8.686f, 23.816f, 8.686f)
        close()
      }
      path(fill = SolidColor(Color(0xFF436DCD))) {
        moveTo(19.987f, 16.92f)
        curveTo(19.433f, 16.366f, 18.537f, 16.354f, 17.968f, 16.893f)
        lineTo(13.2f, 21.409f)
        verticalLineTo(26.057f)
        horizontalLineTo(23.816f)
        curveTo(24.349f, 26.057f, 24.781f, 25.625f, 24.781f, 25.092f)
        verticalLineTo(21.714f)
        lineTo(19.987f, 16.92f)
        close()
      }
      path(fill = SolidColor(Color(0xFFFFFFFF))) {
        moveTo(19.233f, 14.477f)
        curveTo(20.166f, 14.477f, 20.922f, 13.72f, 20.922f, 12.788f)
        curveTo(20.922f, 11.855f, 20.166f, 11.099f, 19.233f, 11.099f)
        curveTo(18.3f, 11.099f, 17.544f, 11.855f, 17.544f, 12.788f)
        curveTo(17.544f, 13.72f, 18.3f, 14.477f, 19.233f, 14.477f)
        close()
      }
      path(
        fill = Brush.linearGradient(
          colorStops = arrayOf(
            0f to Color(0xFF124787),
            0.923f to Color(0xFF173B75),
            1f to Color(0xFF173A73)
          ),
          start = Offset(16.052f, 14.54f),
          end = Offset(16.052f, 26.257f)
        )
      ) {
        moveTo(8.375f, 26.057f)
        horizontalLineTo(23.816f)
        curveTo(24.208f, 26.057f, 24.544f, 25.823f, 24.695f, 25.487f)
        lineTo(14.197f, 14.99f)
        curveTo(13.643f, 14.435f, 12.747f, 14.423f, 12.178f, 14.962f)
        lineTo(7.41f, 19.479f)
        verticalLineTo(25.092f)
        curveTo(7.41f, 25.625f, 7.842f, 26.057f, 8.375f, 26.057f)
        close()
      }
    }.build()

    return _Image!!
  }

@Suppress("ObjectPropertyName")
private var _Image: ImageVector? = null
