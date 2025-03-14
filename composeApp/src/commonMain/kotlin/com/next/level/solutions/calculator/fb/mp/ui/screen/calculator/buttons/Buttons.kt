package com.next.level.solutions.calculator.fb.mp.ui.screen.calculator.buttons

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

class Buttons {
  val zero = "0"
  val one = "1"
  val two = "2"
  val three = "3"
  val four = "4"
  val five = "5"
  val six = "6"
  val seven = "7"
  val eight = "8"
  val nine = "9"

  val point = "."

  val multiply = "*"
  val divide = "/"
  val minus = "-"
  val plus = "+"
  val equal = "="

  val percent = "%"
  val delete = "d"
  val clear = "AC"
  val doubleZero = "00"

  fun getButtons(): ImmutableList<ImmutableList<String>> {
    return persistentListOf(
      persistentListOf(clear, seven, four, one, doubleZero),
      persistentListOf(delete, eight, five, two, zero),
      persistentListOf(percent, nine, six, three, point),
      persistentListOf(divide, multiply, minus, plus, equal),
    )
  }
}

fun String.operation(): Boolean {
  return this == "*"
      || this == "/"
      || this == "-"
      || this == "+"
      || this == "="
}