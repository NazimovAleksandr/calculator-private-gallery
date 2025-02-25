package com.next.level.solutions.calculator.fb.mp.expect

import java.text.SimpleDateFormat
import java.util.Locale

actual fun getDateFormat(pattern: String): DateFormat {
  val dateFormatter = SimpleDateFormat(pattern, Locale.getDefault())

  return object : DateFormat {
    override fun format(date: Long): String {
      return dateFormatter.format(date)
    }
  }
}