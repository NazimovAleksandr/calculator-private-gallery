package com.next.level.solutions.calculator.fb.mp.ui.screen.hidden.files.dialog

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.AdsManager
import com.next.level.solutions.calculator.fb.mp.extensions.core.instance
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.PickerType
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent.DialogConfiguration

class ChooseDialogComponent(
  componentContext: ComponentContext,
  adsManager: AdsManager,
  private val dialogNavigation: SlotNavigation<DialogConfiguration>,
) : RootComponent.Child(adsManager), ComponentContext by componentContext {

  private val handler: Handler = instance<Handler>(componentContext)

  private val _model: MutableValue<Model> by lazy { MutableValue(handler.toModel()) }
  val model: Value<Model> get() = _model

  @Composable
  override fun content() {
    ChooseDialogContent(component = this)
  }

  override fun action(action: RootComponent.Child.Action) {
    when (action) {
      is Action.Hide -> dialogNavigation.dismiss()
      is Action.Gallery -> handler.open(action)
      is Action.Folder -> handler.open(action)
    }
  }

  /**
   * Component contract - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
   */
  class Handler(
    private val fileType: PickerType,
    val open: (Action) -> Unit,
  ) : InstanceKeeper.Instance {
    fun toModel() = Model(
      fileType = fileType,
    )
  }

  data class Model(
    val fileType: PickerType,
  )

  sealed interface Action : RootComponent.Child.Action {
    object Hide: Action
    object Gallery: Action
    object Folder: Action
  }
}