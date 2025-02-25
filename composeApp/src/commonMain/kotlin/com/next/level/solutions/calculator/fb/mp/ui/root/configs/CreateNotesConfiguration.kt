@file:Suppress("PackageDirectoryMismatch")

package com.next.level.solutions.calculator.fb.mp.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.next.level.solutions.calculator.fb.mp.entity.ui.NoteModelUI
import com.next.level.solutions.calculator.fb.mp.ui.screen.create.notes.CreateNotesComponent
import com.next.level.solutions.calculator.fb.mp.utils.KoinFactory
import kotlinx.serialization.Serializable

@Serializable
data class CreateNotesConfiguration(
  private val note: NoteModelUI?,
) : RootComponent.Configuration {
  override fun instanceKeeper(): InstanceKeeper.Instance {
    return CreateNotesComponent.Handler(note)
  }

  override fun KoinFactory.get(context: ComponentContext): RootComponent.Child {
    return componentOf(::CreateNotesComponent, context)
  }
}

fun RootComponent.Configuration.Companion.createNotes(note: NoteModelUI?): CreateNotesConfiguration {
  return CreateNotesConfiguration(note)
}