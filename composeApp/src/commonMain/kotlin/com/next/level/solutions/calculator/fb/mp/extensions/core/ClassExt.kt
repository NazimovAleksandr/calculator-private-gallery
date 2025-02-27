package com.next.level.solutions.calculator.fb.mp.extensions.core

@OptIn(ExperimentalStdlibApi::class)
private val myHexFormat = HexFormat {
  upperCase = false
  number.prefix = "##"
  number.removeLeadingZeros = true
}

@OptIn(ExperimentalStdlibApi::class)
fun Any.hexString(): String {
  return hashCode().toHexString(myHexFormat)
}