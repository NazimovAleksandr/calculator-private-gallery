package com.next.level.solutions.calculator.fb.mp.ui.icons.all

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons
import kotlin.Suppress

@Suppress("UnusedReceiverParameter")
val MagicIcons.All.Photos: ImageVector
  get() {
    if (_Photos != null) {
      return _Photos!!
    }
    _Photos = ImageVector.Builder(
      name = "All.Photos",
      defaultWidth = 37.dp,
      defaultHeight = 32.dp,
      viewportWidth = 37f,
      viewportHeight = 31f
    ).apply {
      path(fill = SolidColor(Color(0xFFEAEFF4))) {
        moveTo(5.333f, 0.5f)
        lineTo(31.333f, 0.5f)
        arcTo(5f, 5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 36.333f, 5.5f)
        lineTo(36.333f, 25.5f)
        arcTo(5f, 5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 31.333f, 30.5f)
        lineTo(5.333f, 30.5f)
        arcTo(5f, 5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.333f, 25.5f)
        lineTo(0.333f, 5.5f)
        arcTo(5f, 5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 5.333f, 0.5f)
        close()
      }
      path(fill = SolidColor(Color(0xFFFFC107))) {
        moveTo(13.426f, 9.82f)
        curveTo(13.426f, 11.854f, 11.777f, 13.503f, 9.743f, 13.503f)
        curveTo(7.709f, 13.503f, 6.06f, 11.854f, 6.06f, 9.82f)
        curveTo(6.06f, 7.785f, 7.709f, 6.136f, 9.743f, 6.136f)
        curveTo(11.777f, 6.136f, 13.426f, 7.785f, 13.426f, 9.82f)
        close()
      }
      path(fill = SolidColor(Color(0xFF388E8E))) {
        moveTo(36.333f, 21.623f)
        verticalLineTo(26.553f)
        curveTo(36.333f, 28.728f, 34.542f, 30.5f, 32.342f, 30.5f)
        horizontalLineTo(4.848f)
        curveTo(4.671f, 30.5f, 4.493f, 30.482f, 4.333f, 30.465f)
        lineTo(22.604f, 12.395f)
        curveTo(23.81f, 11.202f, 25.797f, 11.202f, 27.003f, 12.395f)
        lineTo(36.333f, 21.623f)
        close()
      }
      path(fill = SolidColor(Color(0xFF44ACAC))) {
        moveTo(25.333f, 30.5f)
        horizontalLineTo(4.319f)
        curveTo(4.141f, 30.5f, 3.963f, 30.482f, 3.803f, 30.464f)
        curveTo(1.935f, 30.234f, 0.476f, 28.709f, 0.333f, 26.829f)
        lineTo(8.785f, 18.404f)
        curveTo(9.995f, 17.199f, 11.988f, 17.199f, 13.198f, 18.404f)
        lineTo(25.333f, 30.5f)
        close()
      }
    }.build()

    return _Photos!!
  }

@Suppress("ObjectPropertyName")
private var _Photos: ImageVector? = null
