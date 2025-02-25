package com.next.level.solutions.calculator.fb.mp.expect

interface DateFormat {
  fun format(date: Long): String
}

expect fun getDateFormat(pattern: String): DateFormat