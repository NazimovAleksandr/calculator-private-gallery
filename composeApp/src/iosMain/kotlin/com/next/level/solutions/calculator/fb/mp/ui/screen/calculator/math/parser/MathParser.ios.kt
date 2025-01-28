package com.next.level.solutions.calculator.fb.mp.ui.screen.calculator.math.parser

import platform.Foundation.NSExpression
import platform.Foundation.NSNumber

actual fun getMathParser(): MathParser {
  return object : MathParser {
    override fun calculate(expression: String): Double {
      val nsExpression = NSExpression.expressionWithFormat(expression)
      val result = nsExpression.expressionValueWithObject(null, null) as? NSNumber
      return result?.doubleValue ?: 0.0
    }
  }
}