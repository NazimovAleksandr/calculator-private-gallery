package com.next.level.solutions.calculator.fb.mp.entity._mapper

import com.next.level.solutions.calculator.fb.mp.entity.db.FileModelDB
import com.next.level.solutions.calculator.fb.mp.entity.ui.FileModelUI

fun FileModelDB.toUI(): FileModelUI = FileModelUI(
  path = path,
  name = name,
  folder = folder,
  size = size,
  dateAdded = dateAdded,
  dateHidden = dateHidden,
  dateModified = dateModified,
  hiddenPath = hiddenPath,
)

fun FileModelUI.toDB(): FileModelDB = FileModelDB(
  path = path,
  name = name,
  folder = folder,
  size = size,
  dateAdded = dateAdded,
  dateHidden = dateHidden,
  dateModified = dateModified,
  hiddenPath = hiddenPath,
)