@file:Suppress("PackageDirectoryMismatch")

package com.next.level.solutions.calculator.fb.mp.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.PickerType
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.PickerMode
import com.next.level.solutions.calculator.fb.mp.ui.screen.hidden.files.HiddenFilesComponent
import com.next.level.solutions.calculator.fb.mp.utils.KoinFactory
import kotlinx.serialization.Serializable

@Serializable
data class HiddenFilesConfiguration(
  private val fileType: PickerType,
  private val viewType: PickerMode,
) : RootComponent.Configuration {
  override fun instanceKeeper(): InstanceKeeper.Instance {
    return HiddenFilesComponent.Handler(
      fileType = fileType,
      viewType = viewType,
    )
  }
  override fun KoinFactory.get(context: ComponentContext): RootComponent.Child {
    return componentOf(::HiddenFilesComponent, context)
  }
}

fun RootComponent.Configuration.Companion.hiddenFiles(
  fileType: PickerType,
  viewType: PickerMode,
): HiddenFilesConfiguration {
  return HiddenFilesConfiguration(
    fileType = fileType,
    viewType = viewType,
  )
}