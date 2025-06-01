package com.next.level.solutions.calculator.fb.mp.ui.screen.calculator.composable

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.next.level.solutions.calculator.fb.mp.expect.PlatformExp

@Composable
fun EnteredNumber(
  text: String,
  modifier: Modifier = Modifier,
  style: TextStyle = LocalTextStyle.current,
) {
  val annotatedText = buildAnnotatedString {
    text.forEach {
      when (it) {
        '*', '+', '-', '/' -> withStyle(
          block = { append(it) },
          style = SpanStyle(
            color = style.color.copy(alpha = 0.3f),
            letterSpacing = if (PlatformExp.isIOS) (-3).sp else 12.sp
          ),
        )

        else -> append(it)
      }
    }
  }

  BasicText(
    text = annotatedText,
    maxLines = 1,
    style = style,
    autoSize = TextAutoSize.StepBased(
      minFontSize = 12.sp,
      maxFontSize = style.fontSize,
      stepSize = 0.25.sp,
    ),
    modifier = modifier
      .height(height = style.lineHeight.value.dp)
      .wrapContentHeight()
  )
}
