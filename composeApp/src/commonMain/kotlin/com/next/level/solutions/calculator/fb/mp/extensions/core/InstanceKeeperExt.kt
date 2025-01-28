package com.next.level.solutions.calculator.fb.mp.extensions.core

import com.arkivanov.essenty.instancekeeper.InstanceKeeper

inline fun <reified T> InstanceKeeper.getInstance(key: Any) = get(key) as T