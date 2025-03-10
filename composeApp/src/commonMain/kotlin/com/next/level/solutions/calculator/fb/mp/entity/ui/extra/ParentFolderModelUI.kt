@file:Suppress("PackageDirectoryMismatch")

package com.next.level.solutions.calculator.fb.mp.entity.ui

import com.next.level.solutions.calculator.fb.mp.entity.db.FileDataDB
import com.next.level.solutions.calculator.fb.mp.entity.db.TrashModelDB
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.PickerType

data class ParentFolderModelUI(
  override val path: String,
  override val name: String,
  override val folder: String = "",
  override val size: Long = 0,
  override val dateAdded: String = "",
  override val dateHidden: String = "",
  override val dateModified: String = "",
  override val hiddenPath: String? = null,
) : FileDataUI {
  override var fileType: PickerType = PickerType.File
  override var duration: String = ""

  override fun toDB(): FileDataDB = throw Exception(
    "ParentFolderModelUI cannot be converted to FileDataDB",
  )

  override fun toTrashDB(): TrashModelDB = throw Exception(
    "ParentFolderModelUI cannot be converted to TrashModelDB",
  )
}