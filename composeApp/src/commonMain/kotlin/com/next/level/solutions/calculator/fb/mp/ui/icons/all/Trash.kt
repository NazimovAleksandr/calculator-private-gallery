package com.next.level.solutions.calculator.fb.mp.ui.icons.all

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons

@Suppress("UnusedReceiverParameter")
val MagicIcons.All.Trash: ImageVector
  get() {
    if (_Trash != null) {
      return _Trash!!
    }
    _Trash = ImageVector.Builder(
      name = "All.Trash",
      defaultWidth = 37.dp,
      defaultHeight = 32.dp,
      viewportWidth = 37f,
      viewportHeight = 31f
    ).apply {
      path(fill = SolidColor(Color(0xFFAC4444))) {
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
      path(
        stroke = SolidColor(Color(0xFFFFFFFF)),
        strokeLineWidth = 1.5f,
        strokeLineCap = StrokeCap.Round
      ) {
        moveTo(24.917f, 10.083f)
        lineTo(24.401f, 18.438f)
        curveTo(24.269f, 20.572f, 24.203f, 21.639f, 23.668f, 22.406f)
        curveTo(23.403f, 22.786f, 23.063f, 23.106f, 22.668f, 23.347f)
        curveTo(21.869f, 23.833f, 20.799f, 23.833f, 18.661f, 23.833f)
        curveTo(16.52f, 23.833f, 15.449f, 23.833f, 14.649f, 23.346f)
        curveTo(14.254f, 23.105f, 13.914f, 22.784f, 13.649f, 22.404f)
        curveTo(13.114f, 21.635f, 13.05f, 20.567f, 12.921f, 18.429f)
        lineTo(12.417f, 10.083f)
      }
      path(
        stroke = SolidColor(Color(0xFFFFFFFF)),
        strokeLineWidth = 1.5f,
        strokeLineCap = StrokeCap.Round
      ) {
        moveTo(26.167f, 10.083f)
        horizontalLineTo(11.167f)
      }
      path(
        stroke = SolidColor(Color(0xFFFFFFFF)),
        strokeLineWidth = 1.5f,
        strokeLineCap = StrokeCap.Round
      ) {
        moveTo(22.048f, 10.083f)
        lineTo(21.479f, 8.91f)
        curveTo(21.102f, 8.13f, 20.913f, 7.74f, 20.587f, 7.497f)
        curveTo(20.514f, 7.443f, 20.438f, 7.395f, 20.358f, 7.354f)
        curveTo(19.997f, 7.167f, 19.564f, 7.167f, 18.697f, 7.167f)
        curveTo(17.809f, 7.167f, 17.365f, 7.167f, 16.998f, 7.362f)
        curveTo(16.917f, 7.405f, 16.839f, 7.455f, 16.766f, 7.511f)
        curveTo(16.437f, 7.764f, 16.253f, 8.168f, 15.884f, 8.976f)
        lineTo(15.379f, 10.083f)
      }
    }.build()

    return _Trash!!
  }

@Suppress("ObjectPropertyName")
private var _Trash: ImageVector? = null
