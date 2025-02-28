package com.next.level.solutions.calculator.fb.mp.utils

inline fun <T, R> withNotNull(receiver: T?, block: T.() -> R): R? {
  return receiver?.let{ with(it, block) }
}