package com.next.level.solutions.calculator.fb.mp.entity._mapper

import com.next.level.solutions.calculator.fb.mp.entity.db.FileModelDB
import com.next.level.solutions.calculator.fb.mp.entity.db.TrashModelDB
import com.next.level.solutions.calculator.fb.mp.entity.ui.FileModelUI
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.FilePickerFileType

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

fun FileModelUI.toTrashDB(): TrashModelDB = TrashModelDB(
  type = FilePickerFileType.File.name,
  path = path,
  name = name,
  folder = folder,
  size = size,
  dateAdded = dateAdded,
  dateHidden = dateHidden,
  dateModified = dateModified,
  hiddenPath = hiddenPath,
)