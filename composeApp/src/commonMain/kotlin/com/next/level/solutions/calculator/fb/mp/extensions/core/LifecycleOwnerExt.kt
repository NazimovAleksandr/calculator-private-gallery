package com.next.level.solutions.calculator.fb.mp.extensions.core

import com.arkivanov.essenty.lifecycle.LifecycleOwner
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun LifecycleOwner.launchMain(
  scope: CoroutineScope = coroutineScope(),
  start: CoroutineStart = CoroutineStart.DEFAULT,
  block: suspend CoroutineScope.() -> Unit,
): Job = scope.launch(
  context = Dispatchers.Main,
  start = start,
  block = block,
)

fun LifecycleOwner.launchIO(
  scope: CoroutineScope = coroutineScope(),
  start: CoroutineStart = CoroutineStart.DEFAULT,
  block: suspend CoroutineScope.() -> Unit,
): Job = scope.launch(
  context = Dispatchers.IO,
  start = start,
  block = block,
)