package com.next.level.solutions.calculator.fb.mp.entity.ui

import kotlinx.serialization.Serializable

@Serializable
class BrowserHistoryUI(
  val title: String,
  val url: String,
  val date: String,
  val time: Long,
  val id: Int? = null,
)