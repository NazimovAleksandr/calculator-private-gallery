package com.next.level.solutions.calculator.fb.mp.entity._mapper

import com.next.level.solutions.calculator.fb.mp.entity.db.PhotoModelDB
import com.next.level.solutions.calculator.fb.mp.entity.db.TrashModelDB
import com.next.level.solutions.calculator.fb.mp.entity.ui.PhotoModelUI
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.FilePickerFileType

fun PhotoModelDB.toUI(): PhotoModelUI = PhotoModelUI(
  path = path,
  name = name,
  folder = folder,
  size = size,
  dateAdded = dateAdded,
  dateHidden = dateHidden,
  dateModified = dateModified,
  hiddenPath = hiddenPath,
)

fun PhotoModelUI.toDB(): PhotoModelDB = PhotoModelDB(
  path = path,
  name = name,
  folder = folder,
  size = size,
  dateAdded = dateAdded,
  dateHidden = dateHidden,
  dateModified = dateModified,
  hiddenPath = hiddenPath,
)

fun PhotoModelUI.toTrashDB(): TrashModelDB = TrashModelDB(
  type = FilePickerFileType.Photo.name,
  path = path,
  name = name,
  folder = folder,
  size = size,
  dateAdded = dateAdded,
  dateHidden = dateHidden,
  dateModified = dateModified,
  hiddenPath = hiddenPath,
)