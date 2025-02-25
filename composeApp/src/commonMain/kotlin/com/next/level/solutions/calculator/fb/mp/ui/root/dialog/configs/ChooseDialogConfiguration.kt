@file:Suppress("PackageDirectoryMismatch")

package com.next.level.solutions.calculator.fb.mp.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.FilePickerFileType
import com.next.level.solutions.calculator.fb.mp.ui.screen.hidden.files.dialog.ChooseDialogComponent
import com.next.level.solutions.calculator.fb.mp.ui.screen.hidden.files.dialog.ChooseDialogComponent.Action
import com.next.level.solutions.calculator.fb.mp.utils.KoinFactory
import kotlinx.serialization.Serializable

@Serializable
data class ChooseDialogConfiguration(
  private val fileType: FilePickerFileType,
  private val open: (Action) -> Unit,
) : RootComponent.DialogConfiguration {
  override fun instanceKeeper(): InstanceKeeper.Instance {
    return ChooseDialogComponent.Handler(
      fileType = fileType,
      open = open,
    )
  }

  override fun KoinFactory.get(context: ComponentContext): RootComponent.Child {
    return componentOf(::ChooseDialogComponent, context)
  }
}

fun RootComponent.DialogConfiguration.Companion.chooseDialog(
  fileType: FilePickerFileType,
  open: (Action) -> Unit,
): ChooseDialogConfiguration {
  return ChooseDialogConfiguration(
    fileType = fileType,
    open = open,
  )
}