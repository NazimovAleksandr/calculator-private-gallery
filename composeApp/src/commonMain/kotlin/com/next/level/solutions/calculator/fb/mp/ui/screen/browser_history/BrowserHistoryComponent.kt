package com.next.level.solutions.calculator.fb.mp.ui.screen.browser_history

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.next.level.solutions.calculator.fb.mp.data.database.AppDatabase
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.AdsManager
import com.next.level.solutions.calculator.fb.mp.entity.ui.BrowserHistoryUI
import com.next.level.solutions.calculator.fb.mp.expect.getDateFormat
import com.next.level.solutions.calculator.fb.mp.extensions.core.instance
import com.next.level.solutions.calculator.fb.mp.extensions.core.launchIO
import com.next.level.solutions.calculator.fb.mp.extensions.core.launchMain
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent.Child
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent.DialogConfiguration
import com.next.level.solutions.calculator.fb.mp.ui.root.deleteAllDialog
import com.next.level.solutions.calculator.fb.mp.ui.screen.browser_history.dialog.DeleteAllDialogComponent
import com.next.level.solutions.calculator.fb.mp.ui.screen.browser_history.model.HistoryItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class BrowserHistoryComponent(
  componentContext: ComponentContext,
  private val adsManager: AdsManager,
  private val appDatabase: AppDatabase,
  private val dialogNavigation: SlotNavigation<DialogConfiguration>,
) : Child(adsManager), ComponentContext by componentContext {

  private val handler: Handler = instance<Handler>(componentContext)

  private val _model: MutableValue<Model> by lazy { MutableValue(initialModel()) }
  val model: Value<Model> get() = _model

  private val innerDialogNavigation: SlotNavigation<DialogConfiguration> = SlotNavigation()

  val dialog: Value<ChildSlot<*, Child>> = childSlot(
    source = innerDialogNavigation,
    serializer = DialogConfiguration.serializer(),
    handleBackButton = true,
    childFactory = ::child,
  )

  @Composable
  override fun content() {
    BrowserHistoryContent(component = this)
  }

  override fun action(action: Child.Action) {
    launchMain {
      action.doSomething()?.let {
        action(it)
      }
    }
  }

  private fun initialModel(): Model {
    return Model(
      items = appDatabase.fetchBrowserHistory().prepare(),
    )
  }

  private fun child(
    configuration: DialogConfiguration,
    componentContext: ComponentContext,
  ): Child = configuration.toChild(
    componentContext = componentContext,
  )

  private fun DialogConfiguration.toChild(
    componentContext: ComponentContext,
  ): Child {
    componentContext.instanceKeeper.put(componentContext, instanceKeeper())
    return DeleteAllDialogComponent(
      componentContext = componentContext,
      adsManager = adsManager,
      dialogNavigation = innerDialogNavigation,
    )
  }

  private fun Child.Action.doSomething(): Action? {
    when (this) {
      is Action.Back -> dialogNavigation.dismiss()
      is Action.OpenItem -> handler.open(item.url)
      is Action.DeleteItem -> launchIO { appDatabase.delete(item) }
      is Action.DeleteAllDialog -> innerDialogNavigation.activate(DialogConfiguration.deleteAllDialog { deleteAllHistory() })
    }

    return null
  }

  private fun deleteAllHistory() {
    launchIO {
      val items: List<BrowserHistoryUI> = _model.value.items.first().mapNotNull { it.data }
      appDatabase.delete(*items.toTypedArray())
//      adsManager.inter.show { }
    }
  }

  private fun Flow<List<BrowserHistoryUI>>.prepare(): Flow<ImmutableList<HistoryItem>> {
    val dateFormat = getDateFormat("dd MMM yyyy")

    return map { list ->
      list.groupBy { dateFormat.format(it.time).lowercase() }
        .toHistoryItem()
        .toImmutableList()
    }
  }

  private fun Map<String, List<BrowserHistoryUI>>.toHistoryItem(): List<HistoryItem> {
    return flatMap { (date, entries) ->
      listOf(HistoryItem(date)) + entries.map { HistoryItem(date, it) }
    }
  }

  /**
   * Component contract - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
   */
  class Handler(
    val open: (url: String) -> Unit,
  ) : InstanceKeeper.Instance

  data class Model(
    val items: Flow<ImmutableList<HistoryItem>>,
  )

  sealed interface Action : Child.Action {
    object Back : Action
    class OpenItem(val item: BrowserHistoryUI) : Action
    class DeleteItem(val item: BrowserHistoryUI) : Action
    object DeleteAllDialog : Action
  }
}