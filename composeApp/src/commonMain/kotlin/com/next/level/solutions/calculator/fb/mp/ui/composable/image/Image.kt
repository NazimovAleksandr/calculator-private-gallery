package com.next.level.solutions.calculator.fb.mp.ui.composable.image

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale

//@Composable
//fun Image(
//  @DrawableRes id: Int,
//  modifier: Modifier = Modifier,
//  contentDescription: String? = null,
//  alignment: Alignment = Alignment.Center,
//  contentScale: ContentScale = ContentScale.Fit,
//  alpha: Float = DefaultAlpha,
//  colorFilter: ColorFilter? = null,
//  filterQuality: FilterQuality = DefaultFilterQuality
//) {
//  val bitmap = ImageBitmap.imageResource(id = id)
//  val bitmapPainter = remember(bitmap) { BitmapPainter(bitmap, filterQuality = filterQuality) }
//
//  androidx.compose.foundation.Image(
//    painter = bitmapPainter,
//    contentDescription = contentDescription,
//    modifier = modifier,
//    alignment = alignment,
//    contentScale = contentScale,
//    alpha = alpha,
//    colorFilter = colorFilter
//  )
//}

@Composable
fun Image(
  vector: ImageVector?,
  modifier: Modifier = Modifier,
  contentDescription: String? = null,
  alignment: Alignment = Alignment.Center,
  contentScale: ContentScale = ContentScale.Fit,
  alpha: Float = DefaultAlpha,
  colorFilter: Color,
) = Image(
  vector = vector,
  contentDescription = contentDescription,
  modifier = modifier,
  alignment = alignment,
  contentScale = contentScale,
  alpha = alpha,
  colorFilter = ColorFilter.tint(color = colorFilter)
)

@Composable
fun Image(
  vector: ImageVector?,
  modifier: Modifier = Modifier,
  contentDescription: String? = null,
  alignment: Alignment = Alignment.Center,
  contentScale: ContentScale = ContentScale.Fit,
  alpha: Float = DefaultAlpha,
  colorFilter: ColorFilter? = null
) {
  vector ?: return

  androidx.compose.foundation.Image(
    painter = rememberVectorPainter(vector),
    contentDescription = contentDescription,
    modifier = modifier,
    alignment = alignment,
    contentScale = contentScale,
    alpha = alpha,
    colorFilter = colorFilter
  )
}