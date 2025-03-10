package com.next.level.solutions.calculator.fb.mp.ui.screen.calculator.buttons

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

class Buttons {
  val zero = '0'
  val one = '1'
  val two = '2'
  val three = '3'
  val four = '4'
  val five = '5'
  val six = '6'
  val seven = '7'
  val eight = '8'
  val nine = '9'

  val point = '.'
  val equal = '='
  val delete = 'd'
  val multiply = '*'
  val divide = '/'
  val minus = '-'
  val plus = '+'

  fun getButtons(): ImmutableList<ImmutableList<Char>> {
    return persistentListOf(
      persistentListOf(seven, four, one, delete),
      persistentListOf(eight, five, two, zero),
      persistentListOf(nine, six, three, point),
      persistentListOf(divide, multiply, minus, plus, equal),
    )
  }
}

fun Char.operation(): Boolean {
  return this == '*'
      || this == '/'
      || this == '-'
      || this == '+'
      || this == '='
}