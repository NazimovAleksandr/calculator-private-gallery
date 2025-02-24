package com.next.level.solutions.calculator.fb.mp.entity._mapper

import com.next.level.solutions.calculator.fb.mp.entity.db.NoteModelDB
import com.next.level.solutions.calculator.fb.mp.entity.ui.NoteModelUI

fun NoteModelDB.toUI(): NoteModelUI = NoteModelUI(
  dateAdded = dateAdded,
  dateModified = dateModified,
  name = name,
  note = note,
  path = path,
)

fun NoteModelUI.toDB(): NoteModelDB = NoteModelDB(
  dateAdded = dateAdded,
  dateModified = dateModified,
  name = name,
  note = note,
  path = path,
)