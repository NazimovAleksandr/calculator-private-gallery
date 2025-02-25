package com.next.level.solutions.calculator.fb.mp.file.hider

import com.next.level.solutions.calculator.fb.mp.entity.ui.FileDataUI
import com.next.level.solutions.calculator.fb.mp.entity.ui.PhotoModelUI
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.FilePickerFileType
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.FilePickerViewType
import kotlin.reflect.KFunction8

actual fun getFileHider(): FileHider {
  return FileHiderImpl()
}

class FileHiderImpl : FileHider {

  override fun hiddenFiles(
    fileType: FilePickerFileType,
  ): List<FileDataUI> {
    // TODO("Not yet implemented")
    return emptyList()
  }

  override fun visibleFiles(
    fileType: FilePickerFileType,
    viewType: FilePickerViewType,
  ): List<FileDataUI> {
    // TODO("Not yet implemented")
    return emptyList()
  }

  override fun visibleFiles(
    folder: String?,
    fromParent: Boolean,
    fileType: FilePickerFileType,
    callBack: (List<FileDataUI>) -> Unit,
  ) {
    // TODO("Not yet implemented")
  }

  override suspend fun moveToHiddenFiles(
    fileType: FilePickerFileType,
    files: List<FileDataUI>,
  ): List<FileDataUI> {
    // TODO("Not yet implemented")
    return emptyList()
  }

  override suspend fun restoreFiles(
    files: List<FileDataUI>,
  ): List<FileDataUI> {
    // TODO("Not yet implemented")
    return emptyList()
  }

  override suspend fun moveToVisibleFiles(
    files: List<FileDataUI>,
  ): List<FileDataUI> {
    // TODO("Not yet implemented")
    return emptyList()
  }

  override suspend fun moveToDeletedFiles(
    fileType: FilePickerFileType,
    files: List<FileDataUI>,
  ): List<FileDataUI> {
    // TODO("Not yet implemented")
    return emptyList()
  }

  override fun constructorForFileType(
    fileType: FilePickerFileType,
  ): KFunction8<String, String, String, Long, String, String, String, String?, FileDataUI> {
    // TODO("Not yet implemented")
    return ::PhotoModelUI
  }
}