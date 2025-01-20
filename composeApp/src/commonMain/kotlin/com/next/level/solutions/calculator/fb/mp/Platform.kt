package com.next.level.solutions.calculator.fb.mp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform