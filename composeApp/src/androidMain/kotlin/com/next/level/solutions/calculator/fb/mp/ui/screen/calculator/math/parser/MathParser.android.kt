package com.next.level.solutions.calculator.fb.mp.ui.screen.calculator.math.parser

import org.mariuszgromada.math.mxparser.Expression

actual fun getMathParser(): MathParser {
  return object : MathParser {
    override fun calculate(expression: String): Double {
      return Expression(expression).calculate()
    }
  }
}