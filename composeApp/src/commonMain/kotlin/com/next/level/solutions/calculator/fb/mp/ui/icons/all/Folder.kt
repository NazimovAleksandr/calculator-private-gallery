package com.next.level.solutions.calculator.fb.mp.ui.icons.all

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons
import kotlin.Suppress

@Suppress("UnusedReceiverParameter")
val MagicIcons.All.Folder: ImageVector
  get() {
    if (_Files != null) {
      return _Files!!
    }
    _Files = ImageVector.Builder(
      name = "All.Files",
      defaultWidth = 37.dp,
      defaultHeight = 32.dp,
      viewportWidth = 37f,
      viewportHeight = 32f
    ).apply {
      path(
        fill = Brush.linearGradient(
          colorStops = arrayOf(
            0f to Color(0xFFCC793B),
            1f to Color(0xFFF1A54D)
          ),
          start = Offset(18.149f, 15.11f),
          end = Offset(-0.805f, -3.844f)
        )
      ) {
        moveTo(23.235f, 11.193f)
        horizontalLineTo(0.333f)
        verticalLineTo(4.064f)
        curveTo(0.335f, 3.119f, 0.71f, 2.213f, 1.379f, 1.545f)
        curveTo(2.047f, 0.877f, 2.953f, 0.501f, 3.898f, 0.5f)
        horizontalLineTo(15.963f)
        curveTo(16.577f, 0.502f, 17.18f, 0.661f, 17.714f, 0.963f)
        curveTo(18.249f, 1.265f, 18.697f, 1.699f, 19.015f, 2.223f)
        lineTo(23.235f, 8.793f)
        verticalLineTo(11.193f)
        close()
      }
      path(
        fill = Brush.linearGradient(
          colorStops = arrayOf(
            0f to Color(0xFFF2C94C),
            0.816f to Color(0xFFF2C94C),
            1f to Color(0xFFF2C94C)
          ),
          start = Offset(43.956f, 44.479f),
          end = Offset(2.709f, 3.232f)
        )
      ) {
        moveTo(32.769f, 31.866f)
        horizontalLineTo(3.898f)
        curveTo(2.953f, 31.865f, 2.047f, 31.489f, 1.379f, 30.821f)
        curveTo(0.71f, 30.153f, 0.335f, 29.247f, 0.333f, 28.302f)
        verticalLineTo(9.411f)
        curveTo(0.335f, 8.466f, 0.71f, 7.56f, 1.379f, 6.892f)
        curveTo(2.047f, 6.223f, 2.953f, 5.848f, 3.898f, 5.847f)
        horizontalLineTo(32.769f)
        curveTo(33.714f, 5.848f, 34.62f, 6.223f, 35.288f, 6.892f)
        curveTo(35.957f, 7.56f, 36.332f, 8.466f, 36.333f, 9.411f)
        verticalLineTo(28.302f)
        curveTo(36.332f, 29.247f, 35.957f, 30.153f, 35.288f, 30.821f)
        curveTo(34.62f, 31.489f, 33.714f, 31.865f, 32.769f, 31.866f)
        close()
      }
    }.build()

    return _Files!!
  }

@Suppress("ObjectPropertyName")
private var _Files: ImageVector? = null
