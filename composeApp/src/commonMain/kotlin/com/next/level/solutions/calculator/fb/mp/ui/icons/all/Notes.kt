package com.next.level.solutions.calculator.fb.mp.ui.icons.all

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons
import kotlin.Suppress

@Suppress("UnusedReceiverParameter")
val MagicIcons.All.Notes: ImageVector
  get() {
    if (_Notes != null) {
      return _Notes!!
    }
    _Notes = ImageVector.Builder(
      name = "All.Notes",
      defaultWidth = 37.dp,
      defaultHeight = 32.dp,
      viewportWidth = 37f,
      viewportHeight = 31f
    ).apply {
      path(fill = SolidColor(Color(0xFFEAEFF4))) {
        moveTo(5.667f, 0.5f)
        lineTo(31.667f, 0.5f)
        arcTo(5f, 5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 36.667f, 5.5f)
        lineTo(36.667f, 25.5f)
        arcTo(5f, 5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 31.667f, 30.5f)
        lineTo(5.667f, 30.5f)
        arcTo(5f, 5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.667f, 25.5f)
        lineTo(0.667f, 5.5f)
        arcTo(5f, 5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 5.667f, 0.5f)
        close()
      }
      path(fill = SolidColor(Color(0xFF90A4AE))) {
        moveTo(26.377f, 14.081f)
        horizontalLineTo(6.211f)
        curveTo(5.5f, 14.081f, 4.923f, 13.515f, 4.923f, 12.817f)
        curveTo(4.923f, 12.12f, 5.5f, 11.554f, 6.211f, 11.554f)
        horizontalLineTo(26.377f)
        curveTo(27.089f, 11.554f, 27.666f, 12.12f, 27.666f, 12.817f)
        curveTo(27.666f, 13.515f, 27.089f, 14.081f, 26.377f, 14.081f)
        close()
      }
      path(fill = SolidColor(Color(0xFF90A4AE))) {
        moveTo(20.01f, 19.135f)
        horizontalLineTo(6.261f)
        curveTo(5.522f, 19.135f, 4.923f, 18.569f, 4.923f, 17.871f)
        curveTo(4.923f, 17.174f, 5.522f, 16.608f, 6.261f, 16.608f)
        horizontalLineTo(20.01f)
        curveTo(20.749f, 16.608f, 21.348f, 17.174f, 21.348f, 17.871f)
        curveTo(21.348f, 18.569f, 20.749f, 19.135f, 20.01f, 19.135f)
        close()
      }
      path(fill = SolidColor(Color(0xFF90A4AE))) {
        moveTo(14.956f, 24.189f)
        horizontalLineTo(6.261f)
        curveTo(5.522f, 24.189f, 4.923f, 23.623f, 4.923f, 22.925f)
        curveTo(4.923f, 22.228f, 5.522f, 21.662f, 6.261f, 21.662f)
        horizontalLineTo(14.956f)
        curveTo(15.695f, 21.662f, 16.294f, 22.228f, 16.294f, 22.925f)
        curveTo(16.294f, 23.623f, 15.695f, 24.189f, 14.956f, 24.189f)
        close()
      }
      path(fill = SolidColor(Color(0xFF90A4AE))) {
        moveTo(12.547f, 9.027f)
        horizontalLineTo(6.143f)
        curveTo(5.469f, 9.027f, 4.923f, 8.461f, 4.923f, 7.763f)
        curveTo(4.923f, 7.066f, 5.469f, 6.5f, 6.143f, 6.5f)
        horizontalLineTo(12.547f)
        curveTo(13.221f, 6.5f, 13.767f, 7.066f, 13.767f, 7.763f)
        curveTo(13.767f, 8.461f, 13.221f, 9.027f, 12.547f, 9.027f)
        close()
      }
    }.build()

    return _Notes!!
  }

@Suppress("ObjectPropertyName")
private var _Notes: ImageVector? = null
