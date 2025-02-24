package com.next.level.solutions.calculator.fb.mp.entity.ui

import kotlinx.serialization.Serializable

@Serializable
class BrowserHistoryUI(
  val title: String,
  val url: String,
  val date: String,
  val time: Long,
  val isAd: Boolean = false,
  val isRemove: Boolean = false,
  val id: Int? = null,
)