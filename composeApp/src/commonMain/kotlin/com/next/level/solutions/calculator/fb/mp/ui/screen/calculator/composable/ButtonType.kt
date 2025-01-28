package com.next.level.solutions.calculator.fb.mp.ui.screen.calculator.composable

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

enum class ButtonType(
  val value: Char,
  val text: String = value.toString(),
  val operation: Boolean = false,
  val forPassword: Boolean = true,
) {
  Zero(value = '0'),
  One(value = '1'),
  Two(value = '2'),
  Three(value = '3'),
  Four(value = '4'),
  Five(value = '5'),
  Six(value = '6'),
  Seven(value = '7'),
  Eight(value = '8'),
  Nine(value = '9'),

  Point(value = '.', forPassword = false),
  Multiply(text = "×", value = '*', operation = true, forPassword = false),
  Divide(text = "÷", value = '/', operation = true, forPassword = false),
  Minus(text = "—", value = '-', operation = true, forPassword = false),
  Plus(value = '+', operation = true, forPassword = false),
  Equal(value = '=', operation = true),
  Delete(text = "del", value = 'd'),
  ;

  fun isEqual() = this == Equal

  companion object {
    fun getButtons(): ImmutableList<ImmutableList<ButtonType>> {
      return persistentListOf(
        persistentListOf(Seven, Four, One, Delete),
        persistentListOf(Eight, Five, Two, Zero),
        persistentListOf(Nine, Six, Three, Point),
        persistentListOf(Multiply, Divide, Minus, Plus, Equal),
      )
    }
  }
}

fun ButtonType?.isZero(): Boolean {
  return this == ButtonType.Zero
}

fun ButtonType?.isNotZero(): Boolean {
  return this != ButtonType.Zero
}