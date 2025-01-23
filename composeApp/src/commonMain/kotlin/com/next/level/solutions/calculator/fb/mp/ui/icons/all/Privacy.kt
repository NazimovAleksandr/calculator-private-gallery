package com.next.level.solutions.calculator.fb.mp.ui.icons.all

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons

@Suppress("UnusedReceiverParameter")
val MagicIcons.All.Privacy: ImageVector
  get() {
    if (_Privacy != null) {
      return _Privacy!!
    }
    _Privacy = ImageVector.Builder(
      name = "All.Privacy",
      defaultWidth = 20.dp,
      defaultHeight = 22.dp,
      viewportWidth = 20f,
      viewportHeight = 22f
    ).apply {
      path(
        stroke = SolidColor(Color(0xFFF09200)),
        strokeLineWidth = 1.5f,
        strokeLineJoin = StrokeJoin.Round
      ) {
        moveTo(1.5f, 9f)
        curveTo(1.5f, 5.229f, 1.5f, 3.343f, 2.745f, 2.172f)
        curveTo(3.99f, 1f, 5.993f, 1f, 10f, 1f)
        horizontalLineTo(10.773f)
        curveTo(14.034f, 1f, 15.665f, 1f, 16.797f, 1.798f)
        curveTo(17.121f, 2.026f, 17.409f, 2.298f, 17.652f, 2.603f)
        curveTo(18.5f, 3.669f, 18.5f, 5.203f, 18.5f, 8.273f)
        verticalLineTo(10.818f)
        curveTo(18.5f, 13.781f, 18.5f, 15.263f, 18.031f, 16.446f)
        curveTo(17.277f, 18.349f, 15.683f, 19.849f, 13.662f, 20.559f)
        curveTo(12.404f, 21f, 10.83f, 21f, 7.682f, 21f)
        curveTo(5.883f, 21f, 4.983f, 21f, 4.265f, 20.748f)
        curveTo(3.11f, 20.342f, 2.199f, 19.485f, 1.768f, 18.398f)
        curveTo(1.5f, 17.722f, 1.5f, 16.875f, 1.5f, 15.182f)
        verticalLineTo(9f)
        close()
      }
      path(
        stroke = SolidColor(Color(0xFFF09200)),
        strokeLineWidth = 1.5f,
        strokeLineCap = StrokeCap.Round,
        strokeLineJoin = StrokeJoin.Round
      ) {
        moveTo(18.5f, 11f)
        curveTo(18.5f, 12.841f, 17.008f, 14.333f, 15.167f, 14.333f)
        curveTo(14.501f, 14.333f, 13.716f, 14.217f, 13.069f, 14.39f)
        curveTo(12.493f, 14.544f, 12.044f, 14.993f, 11.89f, 15.569f)
        curveTo(11.717f, 16.216f, 11.833f, 17.001f, 11.833f, 17.667f)
        curveTo(11.833f, 19.508f, 10.341f, 21f, 8.5f, 21f)
      }
      path(
        stroke = SolidColor(Color(0xFFF09200)),
        strokeLineWidth = 1.5f,
        strokeLineCap = StrokeCap.Round,
        strokeLineJoin = StrokeJoin.Round
      ) {
        moveTo(6f, 6f)
        horizontalLineTo(13f)
      }
      path(
        stroke = SolidColor(Color(0xFFF09200)),
        strokeLineWidth = 1.5f,
        strokeLineCap = StrokeCap.Round,
        strokeLineJoin = StrokeJoin.Round
      ) {
        moveTo(6f, 10f)
        horizontalLineTo(9f)
      }
    }.build()

    return _Privacy!!
  }

@Suppress("ObjectPropertyName")
private var _Privacy: ImageVector? = null
