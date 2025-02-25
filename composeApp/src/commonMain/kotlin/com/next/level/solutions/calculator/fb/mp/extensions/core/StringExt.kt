package com.next.level.solutions.calculator.fb.mp.extensions.core

fun String.uppercaseFirstChar() = lowercase().replaceFirstChar { it.uppercase() }