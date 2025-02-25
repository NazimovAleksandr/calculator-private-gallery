package com.next.level.solutions.calculator.fb.mp.expect

import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSLocale
import platform.Foundation.NSTimeZone
import platform.Foundation.currentLocale
import platform.Foundation.dateWithTimeIntervalSince1970
import platform.Foundation.defaultTimeZone

actual fun getDateFormat(pattern: String): DateFormat {
  return object : DateFormat {
    private val dateFormatter = NSDateFormatter()

    init {
      dateFormatter.dateFormat = pattern
      dateFormatter.timeZone = NSTimeZone.defaultTimeZone
      dateFormatter.locale = NSLocale.currentLocale
    }

    override fun format(date: Long): String {
      val nsDate = NSDate.dateWithTimeIntervalSince1970(date / 1000.0)

      return dateFormatter.stringFromDate(nsDate)
    }
  }
}