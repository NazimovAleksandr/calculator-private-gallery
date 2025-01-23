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
val MagicIcons.All.TakePicture: ImageVector
  get() {
    if (_TakePicture != null) {
      return _TakePicture!!
    }
    _TakePicture = ImageVector.Builder(
      name = "All.TakePicture",
      defaultWidth = 22.dp,
      defaultHeight = 22.dp,
      viewportWidth = 22f,
      viewportHeight = 22f
    ).apply {
      path(
        stroke = SolidColor(Color(0xFFF09200)),
        strokeLineWidth = 1.5f,
        strokeLineCap = StrokeCap.Round
      ) {
        moveTo(6f, 5.001f)
        curveTo(4.779f, 5.004f, 4.104f, 5.033f, 3.549f, 5.266f)
        curveTo(2.771f, 5.593f, 2.138f, 6.196f, 1.768f, 6.962f)
        curveTo(1.466f, 7.587f, 1.417f, 8.388f, 1.318f, 9.99f)
        lineTo(1.163f, 12.501f)
        curveTo(0.917f, 16.485f, 0.795f, 18.478f, 1.964f, 19.739f)
        curveTo(3.133f, 21f, 5.103f, 21f, 9.042f, 21f)
        horizontalLineTo(12.958f)
        curveTo(16.897f, 21f, 18.867f, 21f, 20.036f, 19.739f)
        curveTo(21.205f, 18.478f, 21.083f, 16.485f, 20.837f, 12.501f)
        lineTo(20.682f, 9.99f)
        curveTo(20.583f, 8.388f, 20.534f, 7.587f, 20.232f, 6.962f)
        curveTo(19.862f, 6.196f, 19.229f, 5.593f, 18.451f, 5.266f)
        curveTo(17.896f, 5.033f, 17.221f, 5.004f, 16f, 5.001f)
      }
      path(
        stroke = SolidColor(Color(0xFFF09200)),
        strokeLineWidth = 1.5f,
        strokeLineCap = StrokeCap.Round,
        strokeLineJoin = StrokeJoin.Round
      ) {
        moveTo(16f, 6f)
        lineTo(15.114f, 3.785f)
        curveTo(14.732f, 2.83f, 14.399f, 1.746f, 13.417f, 1.26f)
        curveTo(12.892f, 1f, 12.262f, 1f, 11f, 1f)
        curveTo(9.738f, 1f, 9.108f, 1f, 8.583f, 1.26f)
        curveTo(7.601f, 1.746f, 7.268f, 2.83f, 6.886f, 3.785f)
        lineTo(6f, 6f)
      }
      path(
        stroke = SolidColor(Color(0xFFF09200)),
        strokeLineWidth = 1.5f
      ) {
        moveTo(14.5f, 13f)
        curveTo(14.5f, 14.933f, 12.933f, 16.5f, 11f, 16.5f)
        curveTo(9.067f, 16.5f, 7.5f, 14.933f, 7.5f, 13f)
        curveTo(7.5f, 11.067f, 9.067f, 9.5f, 11f, 9.5f)
        curveTo(12.933f, 9.5f, 14.5f, 11.067f, 14.5f, 13f)
        close()
      }
      path(
        stroke = SolidColor(Color(0xFFF09200)),
        strokeLineWidth = 2f,
        strokeLineCap = StrokeCap.Round,
        strokeLineJoin = StrokeJoin.Round
      ) {
        moveTo(11f, 5f)
        horizontalLineTo(11.009f)
      }
    }.build()

    return _TakePicture!!
  }

@Suppress("ObjectPropertyName")
private var _TakePicture: ImageVector? = null
