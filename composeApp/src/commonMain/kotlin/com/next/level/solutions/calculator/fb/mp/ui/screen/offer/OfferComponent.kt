package com.next.level.solutions.calculator.fb.mp.ui.screen.offer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.next.level.solutions.calculator.fb.mp.constants.RESET_PASSWORD_CODE
import com.next.level.solutions.calculator.fb.mp.data.datastore.AppDatastore
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.AdsManager
import com.next.level.solutions.calculator.fb.mp.ecosystem.billing.BillingManager
import com.next.level.solutions.calculator.fb.mp.ecosystem.billing.Product
import com.next.level.solutions.calculator.fb.mp.ecosystem.billing.Products
import com.next.level.solutions.calculator.fb.mp.entity.ui.extra.SettingsModelUI
import com.next.level.solutions.calculator.fb.mp.entity.ui.extra.SettingsType
import com.next.level.solutions.calculator.fb.mp.entity.ui.extra.getSettingsItems
import com.next.level.solutions.calculator.fb.mp.expect.PlatformExp
import com.next.level.solutions.calculator.fb.mp.extensions.core.getRootComponent
import com.next.level.solutions.calculator.fb.mp.extensions.core.launchIO
import com.next.level.solutions.calculator.fb.mp.extensions.core.launchMain
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent
import com.next.level.solutions.calculator.fb.mp.ui.root.calculator
import com.next.level.solutions.calculator.fb.mp.ui.root.language
import com.next.level.solutions.calculator.fb.mp.ui.root.policyTos
import com.next.level.solutions.calculator.fb.mp.ui.root.secureQuestion
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.coroutines.flow.Flow

@Stable
class OfferComponent(
  componentContext: ComponentContext,
  adsManager: AdsManager,
  private val appDatastore: AppDatastore,
  private val navigation: StackNavigation<RootComponent.Configuration>,
  private val billingManager: BillingManager,
) : RootComponent.Child(adsManager), ComponentContext by componentContext {

  private val _model: MutableValue<Model> by lazy { MutableValue(initialModel()) }
  val model: Value<Model> get() = _model

  @Composable
  override fun content() {
    OfferContent(component = this)
  }

  override fun action(action: RootComponent.Child.Action) {
    launchMain {
      action.doSomething()?.let {
        action(it)
      }
    }
  }

  private fun initialModel(): Model {
//    analytics.settings.screenOpen()

    return Model(
      bought = billingManager.bought,
      products = billingManager.products,
    )
  }

  private suspend fun RootComponent.Child.Action.doSomething(): Action? {
    when (this) {
      is Action.Back -> navigation.pop()
    }

    return null
  }

  /**
   * Store contract - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
   */
  class Handler : InstanceKeeper.Instance

  data class Model(
    val bought: Flow<Boolean>,
    val products: Flow<List<Product>>,
    val selected: Products = Products.OneYear,
  )

  sealed interface Action : RootComponent.Child.Action {
    object Buy : Action
    object Back : Action
    object Privacy : Action
    object Tos : Action
    class Select(val item: Product) : Action
  }
//
//  sealed interface Signal : Store.Signal {
//    object Rate5Stars: Signal
//    object Share: Signal
//  }
}