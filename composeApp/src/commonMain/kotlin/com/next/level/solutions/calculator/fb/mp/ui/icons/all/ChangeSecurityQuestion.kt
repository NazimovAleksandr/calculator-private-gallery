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
val MagicIcons.All.ChangeSecurityQuestion: ImageVector
  get() {
    if (_ChangeSecurityQuestion != null) {
      return _ChangeSecurityQuestion!!
    }
    _ChangeSecurityQuestion = ImageVector.Builder(
      name = "All.ChangeSecurityQuestion",
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
        strokeLineWidth = 1.5f,
        strokeLineCap = StrokeCap.Round
      ) {
        moveTo(9f, 8f)
        curveTo(9f, 6.895f, 9.895f, 6f, 11f, 6f)
        curveTo(12.105f, 6f, 13f, 6.895f, 13f, 8f)
        curveTo(13f, 8.398f, 12.884f, 8.769f, 12.683f, 9.081f)
        curveTo(12.085f, 10.01f, 11f, 10.895f, 11f, 12f)
        verticalLineTo(12.5f)
      }
      path(
        stroke = SolidColor(Color(0xFFF09200)),
        strokeLineWidth = 2f,
        strokeLineCap = StrokeCap.Round,
        strokeLineJoin = StrokeJoin.Round
      ) {
        moveTo(10.992f, 16f)
        horizontalLineTo(11.001f)
      }
    }.build()

    return _ChangeSecurityQuestion!!
  }

@Suppress("ObjectPropertyName")
private var _ChangeSecurityQuestion: ImageVector? = null
