@file:Suppress("PackageDirectoryMismatch")

package com.next.level.solutions.calculator.fb.mp.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.FilePickerFileType
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.FilePickerViewType
import com.next.level.solutions.calculator.fb.mp.ui.screen.hidden.files.HiddenFilesComponent
import com.next.level.solutions.calculator.fb.mp.utils.KoinFactory
import kotlinx.serialization.Serializable

@Serializable
data class HiddenFilesConfiguration(
  private val fileType: FilePickerFileType,
  private val viewType: FilePickerViewType,
  private val hiddenFilesCount: Int,
) : RootComponent.Configuration {
  override fun instanceKeeper(): InstanceKeeper.Instance {
    return HiddenFilesComponent.Handler(
      fileType = fileType,
      viewType = viewType,
      hiddenFilesCount = hiddenFilesCount,
    )
  }
  override fun KoinFactory.get(context: ComponentContext): RootComponent.Child {
    return componentOf(::HiddenFilesComponent, context)
  }
}

fun RootComponent.Configuration.Companion.hiddenFiles(
  fileType: FilePickerFileType,
  viewType: FilePickerViewType,
  hiddenFilesCount: Int,
): HiddenFilesConfiguration {
  return HiddenFilesConfiguration(
    fileType = fileType,
    viewType = viewType,
    hiddenFilesCount = hiddenFilesCount,
  )
}