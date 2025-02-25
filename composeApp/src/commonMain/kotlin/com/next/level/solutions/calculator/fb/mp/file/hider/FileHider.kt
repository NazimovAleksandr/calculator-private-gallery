package com.next.level.solutions.calculator.fb.mp.file.hider

import com.next.level.solutions.calculator.fb.mp.entity.ui.FileDataUI
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.FilePickerFileType
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.FilePickerViewType
import kotlin.reflect.KFunction8

expect fun getFileHider(): FileHider

interface FileHider {

  fun hiddenFiles(
    fileType: FilePickerFileType,
  ): List<FileDataUI>

  fun visibleFiles(
    fileType: FilePickerFileType,
    viewType: FilePickerViewType,
  ): List<FileDataUI>

  fun visibleFiles(
    folder: String?,
    fromParent: Boolean,
    fileType: FilePickerFileType,
    callBack: (List<FileDataUI>) -> Unit,
  )

  suspend fun moveToHiddenFiles(
    fileType: FilePickerFileType,
    files: List<FileDataUI>,
  ): List<FileDataUI>

  suspend fun restoreFiles(
    files: List<FileDataUI>,
  ): List<FileDataUI>

  suspend fun moveToVisibleFiles(
    files: List<FileDataUI>,
  ): List<FileDataUI>

  suspend fun moveToDeletedFiles(
    fileType: FilePickerFileType,
    files: List<FileDataUI>,
  ): List<FileDataUI>

  fun constructorForFileType(
    fileType: FilePickerFileType,
  ): KFunction8<String, String, String, Long, String, String, String, String?, FileDataUI>
}