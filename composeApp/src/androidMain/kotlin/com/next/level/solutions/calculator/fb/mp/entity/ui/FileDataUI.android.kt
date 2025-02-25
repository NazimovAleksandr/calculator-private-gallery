package com.next.level.solutions.calculator.fb.mp.entity.ui

import java.io.File

actual fun FileDataUI?.toPath(
  parentFolderPath: String?,
): List<FileDataUI> {
  this ?: return emptyList()

  val files: MutableList<File> = mutableListOf()

  val root = File(parentFolderPath ?: "").parentFile
  var parentFile: File? = File(path)

  while (
    parentFile != null
    && parentFile.absolutePath != root?.absolutePath
  ) {
    files.add(parentFile)
    parentFile = parentFile.parentFile
  }

  return files.map {
    ParentFolderModelUI(
      path = it.absolutePath,
      name = it.name,
    )
  }.reversed()
}