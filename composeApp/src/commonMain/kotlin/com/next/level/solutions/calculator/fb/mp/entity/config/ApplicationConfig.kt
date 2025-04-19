package com.next.level.solutions.calculator.fb.mp.entity.config

import kotlinx.serialization.Serializable

@Serializable
class ApplicationConfig(
  val appUpdateType: String = "",
)