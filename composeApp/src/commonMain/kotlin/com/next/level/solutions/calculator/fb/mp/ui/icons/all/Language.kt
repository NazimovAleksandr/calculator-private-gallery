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
val MagicIcons.All.Language: ImageVector
  get() {
    if (_Language != null) {
      return _Language!!
    }
    _Language = ImageVector.Builder(
      name = "All.Language",
      defaultWidth = 22.dp,
      defaultHeight = 22.dp,
      viewportWidth = 22f,
      viewportHeight = 22f
    ).apply {
      path(
        stroke = SolidColor(Color(0xFFF09200)),
        strokeLineWidth = 1.5f
      ) {
        moveTo(11f, 11f)
        moveToRelative(-10f, 0f)
        arcToRelative(10f, 10f, 0f, isMoreThanHalf = true, isPositiveArc = true, 20f, 0f)
        arcToRelative(10f, 10f, 0f, isMoreThanHalf = true, isPositiveArc = true, -20f, 0f)
      }
      path(
        stroke = SolidColor(Color(0xFFF09200)),
        strokeLineWidth = 1.5f
      ) {
        moveTo(7f, 11f)
        arcToRelative(4f, 10f, 0f, isMoreThanHalf = true, isPositiveArc = false, 8f, 0f)
        arcToRelative(4f, 10f, 0f, isMoreThanHalf = true, isPositiveArc = false, -8f, 0f)
        close()
      }
      path(
        stroke = SolidColor(Color(0xFFF09200)),
        strokeLineWidth = 1.5f,
        strokeLineCap = StrokeCap.Round,
        strokeLineJoin = StrokeJoin.Round
      ) {
        moveTo(1f, 11f)
        horizontalLineTo(21f)
      }
    }.build()

    return _Language!!
  }

@Suppress("ObjectPropertyName")
private var _Language: ImageVector? = null
