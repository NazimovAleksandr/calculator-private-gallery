package com.next.level.solutions.calculator.fb.mp.ui.icons.all

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons

@Suppress("UnusedReceiverParameter")
val MagicIcons.All.Videos: ImageVector
  get() {
    if (_Videos != null) {
      return _Videos!!
    }
    _Videos = ImageVector.Builder(
      name = "All.Videos",
      defaultWidth = 36.dp,
      defaultHeight = 32.dp,
      viewportWidth = 36f,
      viewportHeight = 31f
    ).apply {
      path(fill = SolidColor(Color(0xFFE84040))) {
        moveTo(5f, 0.5f)
        lineTo(31f, 0.5f)
        arcTo(5f, 5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 36f, 5.5f)
        lineTo(36f, 25.5f)
        arcTo(5f, 5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 31f, 30.5f)
        lineTo(5f, 30.5f)
        arcTo(5f, 5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0f, 25.5f)
        lineTo(0f, 5.5f)
        arcTo(5f, 5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 5f, 0.5f)
        close()
      }
      path(fill = SolidColor(Color(0xFFFFFFFF))) {
        moveTo(24.263f, 16.313f)
        curveTo(24.933f, 15.928f, 24.933f, 14.962f, 24.263f, 14.578f)
        lineTo(15.164f, 9.359f)
        curveTo(14.497f, 8.977f, 13.667f, 9.458f, 13.667f, 10.226f)
        verticalLineTo(20.664f)
        curveTo(13.667f, 21.432f, 14.497f, 21.913f, 15.164f, 21.531f)
        lineTo(24.263f, 16.313f)
        close()
      }
    }.build()

    return _Videos!!
  }

@Suppress("ObjectPropertyName")
private var _Videos: ImageVector? = null
