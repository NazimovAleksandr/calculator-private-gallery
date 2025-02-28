package com.next.level.solutions.calculator.fb.mp.ui.screen.create.notes

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.next.level.solutions.calculator.fb.mp.data.database.AppDatabase
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.AdsManager
import com.next.level.solutions.calculator.fb.mp.entity.ui.NoteModelUI
import com.next.level.solutions.calculator.fb.mp.expect.currentTimeMillis
import com.next.level.solutions.calculator.fb.mp.extensions.core.instance
import com.next.level.solutions.calculator.fb.mp.extensions.core.launchMain
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.FilePickerFileType
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent
import com.next.level.solutions.calculator.fb.mp.ui.root.lottie

class CreateNotesComponent(
  componentContext: ComponentContext,
  adsManager: AdsManager,
  private val appDatabase: AppDatabase,
  private val navigation: StackNavigation<RootComponent.Configuration>,
) : RootComponent.Child(adsManager), ComponentContext by componentContext {

  private val handler: Handler = instance<Handler>(componentContext)

  private val _model: MutableValue<Model> by lazy { MutableValue(Model(note = handler.note)) }
  val model: Value<Model> get() = _model

  @Composable
  override fun content() {
    CreateNotesContent(component = this)
  }

  override fun action(action: RootComponent.Child.Action) {
    launchMain {
      action.doSomething()?.let {
        action(it)
      }
    }
  }

  private suspend fun RootComponent.Child.Action.doSomething(): Action? {
    when (this) {
      is Action.Back -> navigation.pop()
      is Action.Create -> doSomething()
    }

    return null
  }

  private suspend fun Action.Create.doSomething() {
//    analytics.createNotes.noteCreated()

    appDatabase.add(
      fileType = FilePickerFileType.Note,
      files = listOf(
        _model.value.note?.copy(
          dateModified = currentTimeMillis().toString(),
          name = name,
          note = note,
        ) ?: NoteModelUI(
          dateAdded = currentTimeMillis().toString(),
          name = name,
          note = note,
        )
      )
    )

    navigation.replaceCurrent(RootComponent.Configuration.lottie())
  }

  /**
   * Component contract - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
   */
  class Handler(
    val note: NoteModelUI?,
  ) : InstanceKeeper.Instance

  data class Model(
    val note: NoteModelUI?,
  )

  sealed interface Action : RootComponent.Child.Action {
    object Back : Action
    class Create(val name: String, val note: String) : Action
  }
}