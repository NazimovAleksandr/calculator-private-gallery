package com.next.level.solutions.calculator.fb.mp.file.visibility.manager

import com.next.level.solutions.calculator.fb.mp.entity.ui.FileDataUI
import com.next.level.solutions.calculator.fb.mp.entity.ui.TrashModelUI
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.PickerType
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.PickerMode

interface FileVisibilityManager {
  fun checkInvisibleFiles(): Boolean

  fun invisibleFiles(
    fileType: PickerType,
    forRestore: Boolean = false,
  ): List<FileDataUI>

  fun visibleFiles(
    fileType: PickerType,
    viewType: PickerMode,
  ): List<FileDataUI>

  fun visibleFiles(
    folder: String?,
    fromParent: Boolean,
    fileType: PickerType,
    callBack: (List<FileDataUI>) -> Unit,
  )

  suspend fun moveToInvisibleFiles(
    fileType: PickerType,
    files: List<FileDataUI>,
    callBack: suspend (List<FileDataUI>) -> Unit,
  )

  suspend fun moveToVisibleFiles(
    fileType: PickerType,
    files: List<FileDataUI>,
    callBack: suspend (List<FileDataUI>) -> Unit,
  )

  suspend fun moveToDeletedFiles(
    fileType: PickerType,
    files: List<FileDataUI>,
    callBack: suspend (List<FileDataUI>) -> Unit,
  )

  suspend fun restoreToInvisibleFiles(
    files: List<TrashModelUI>,
    callBack: suspend (List<TrashModelUI>) -> Unit,
  )
}