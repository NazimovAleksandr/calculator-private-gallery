package com.next.level.solutions.calculator.fb.mp.entity._mapper

import com.next.level.solutions.calculator.fb.mp.entity.db.BrowserHistoryDB
import com.next.level.solutions.calculator.fb.mp.entity.ui.BrowserHistoryUI

fun BrowserHistoryDB.toUI(): BrowserHistoryUI = BrowserHistoryUI(
  title = title,
  url = url,
  date = date,
  time = time,
  isAd = isAd,
  isRemove = isRemove,
  id = id,
)

fun BrowserHistoryUI.toDB(): BrowserHistoryDB = BrowserHistoryDB(
  title = title,
  url = url,
  date = date,
  time = time,
  isAd = isAd,
  isRemove = isRemove,
  id = id,
)