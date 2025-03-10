@file:Suppress("PackageDirectoryMismatch")

package com.next.level.solutions.calculator.fb.mp.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.PickerType
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.PickerMode
import com.next.level.solutions.calculator.fb.mp.ui.screen.file.hider.FileHiderComponent
import com.next.level.solutions.calculator.fb.mp.utils.KoinFactory
import kotlinx.serialization.Serializable

@Serializable
data class FileHiderConfiguration(
  private val fileType: PickerType,
  private val viewType: PickerMode,
) : RootComponent.Configuration {
  override fun instanceKeeper(): InstanceKeeper.Instance {
    return FileHiderComponent.Handler(
      fileType = fileType,
      viewType = viewType,
    )
  }

  override fun KoinFactory.get(context: ComponentContext): RootComponent.Child {
    return componentOf(::FileHiderComponent, context)
  }
}

fun RootComponent.Configuration.Companion.fileHider(
  fileType: PickerType,
  viewType: PickerMode,
): FileHiderConfiguration {
  return FileHiderConfiguration(
    fileType = fileType,
    viewType = viewType,
  )
}