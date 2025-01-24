package com.next.level.solutions.calculator.fb.mp.ui.screen.language.model

import kotlinx.serialization.Serializable

@Serializable
data class LanguageModel(
  val name: String,
  val code: String,
  val country: String = "",
  val variant: String = "",
)