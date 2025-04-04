@file:Suppress("PackageDirectoryMismatch")

package com.next.level.solutions.calculator.fb.mp.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.PickerType
import com.next.level.solutions.calculator.fb.mp.ui.screen.hidden.files.dialog.ChooseDialogComponent
import com.next.level.solutions.calculator.fb.mp.ui.screen.hidden.files.dialog.ChooseDialogComponent.Action
import com.next.level.solutions.calculator.fb.mp.utils.KoinFactory
import kotlinx.serialization.Serializable

private var openHandler: (Action) -> Unit = {}

@Serializable
data class ChooseDialogConfiguration(
  private val fileType: PickerType,
) : RootComponent.DialogConfiguration {
  override fun instanceKeeper(): InstanceKeeper.Instance {
    return ChooseDialogComponent.Handler(
      fileType = fileType,
      open = openHandler,
    )
  }

  override fun KoinFactory.get(context: ComponentContext): RootComponent.Child {
    return componentOf(::ChooseDialogComponent, context)
  }
}

fun RootComponent.DialogConfiguration.Companion.chooseDialog(
  fileType: PickerType,
  open: (Action) -> Unit,
): ChooseDialogConfiguration {
  openHandler = open

  return ChooseDialogConfiguration(
    fileType = fileType,
  )
}