package com.next.level.solutions.calculator.fb.mp.ui.screen.browser

import androidx.compose.runtime.Composable
import coil3.Bitmap
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.child
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.popWhile
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.multiplatform.webview.web.NativeWebView
import com.next.level.solutions.calculator.fb.mp.data.database.AppDatabase
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.AdsManager
import com.next.level.solutions.calculator.fb.mp.entity.ui.BrowserHistoryUI
import com.next.level.solutions.calculator.fb.mp.expect.currentTimeMillis
import com.next.level.solutions.calculator.fb.mp.expect.getDateFormat
import com.next.level.solutions.calculator.fb.mp.extensions.core.getRootComponent
import com.next.level.solutions.calculator.fb.mp.extensions.core.instance
import com.next.level.solutions.calculator.fb.mp.extensions.core.launchIO
import com.next.level.solutions.calculator.fb.mp.extensions.core.launchMain
import com.next.level.solutions.calculator.fb.mp.ui.root.HomeConfiguration
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent.Configuration
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent.DialogConfiguration
import com.next.level.solutions.calculator.fb.mp.ui.root.browser
import com.next.level.solutions.calculator.fb.mp.ui.root.browserHistory
import com.next.level.solutions.calculator.fb.mp.ui.screen.browser.composable.browser.BrowserViewState
import com.next.level.solutions.calculator.fb.mp.ui.screen.browser.composable.toolbar.BrowserToolbarState
import io.ktor.http.Url
import kotlin.random.Random

class BrowserComponent(
  componentContext: ComponentContext,
  adsManager: AdsManager,
  private val appDatabase: AppDatabase,
  private val navigation: StackNavigation<Configuration>,
  private val dialogNavigation: SlotNavigation<DialogConfiguration>,
) : RootComponent.Child(adsManager), ComponentContext by componentContext {

  private val rootComponent: RootComponent = getRootComponent()
  private val handler: Handler = instance<Handler>(componentContext)

  private val _model: MutableValue<Model> by lazy { MutableValue(initialModel()) }
  val model: Value<Model> get() = _model

  private val dateFormat = getDateFormat("dd MMM yyyy")

  override fun content(): @Composable () -> Unit = {
    BrowserContent(component = this)
  }

  override fun action(action: RootComponent.Child.Action) {
    action.updateModel()

    launchMain {
      action.doSomething()?.let {
        action(it)
      }
    }
  }

  private fun initialModel(): Model {
    //    analytics.browser.browserOpen()

    val initUrl: String = when (handler.initUrl.startsWith("AddTab")) {
      true -> "https://www.google.com/"
      else -> handler.initUrl
    }

    return Model(
      browserViewState = BrowserViewState(
        initUrl = initUrl,
        loadUrl = initUrl,
      ),
    )
  }

  private fun RootComponent.Child.Action.updateModel() {
    when (this) {
      is Action.Load -> update()
      is Action.ReceivedIcon -> update()

//      is Action.ReceivedIcon -> update()
//      is Action.ReceivedTitle -> update()
//      is Action.PageStarted -> update()
    }
  }

  private fun RootComponent.Child.Action.doSomething(): Action? {
    when (this) {
      is Action.Back -> when (rootComponent.dialog.child) {
        null -> navigation.popWhile { it !is HomeConfiguration }
        else -> dialogNavigation.dismiss()
      }

      is Action.AddTab -> navigation.push(
        Configuration.browser("AddTab ${Random.nextInt()}${Random.nextInt()}")
      )

      is Action.History -> dialogNavigation.activate(
        DialogConfiguration.browserHistory { action(Action.Load(it)) }
      )

      is Action.PageStarted -> doSomething()
      is Action.PageFinished -> doSomething()
      is Action.PageCommitVisible -> doSomething()
    }

    return null
  }

  private fun Action.Load.update() {
    val link = url.validUrl()

    _model.update {
      it.copy(
        browserViewState = _model.value.browserViewState.copy(
          loadUrl = link,
        ),
      )
    }
  }

  private fun Action.ReceivedIcon.update() {
    _model.update {
      it.copy(
        toolbarState = _model.value.toolbarState.copy(
          pageIcon = icon,
        ),
      )
    }
  }

  private fun Action.PageStarted.doSomething() {
    saveHistory(
      title = title,
      url = url,
    )
  }

  private fun Action.PageFinished.doSomething() {
    saveHistory(
      title = title,
      url = url,
    )
  }

  private fun Action.PageCommitVisible.doSomething() {
    saveHistory(
      title = title,
      url = url,
    )
  }

  private fun saveHistory(
    title: String?,
    url: String?,
  ) {
    val time = currentTimeMillis()

    launchIO {
      appDatabase.addBrowserHistory(
        item = BrowserHistoryUI(
          title = title ?: "",
          url = url ?: "",
          date = dateFormat.format(time),
          time = time,
        ),
      )
    }
  }

  private fun String.validUrl(): String {
    return try {
      Url(this)
      this
    } catch (ignored: Exception) {
      try {
        val url = "https://$this/"

        Url(url)
        url
      } catch (ignored: Exception) {
        "https://www.google.com/search?q=$this"
      }
    }
  }

  /**
   * Component contract - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
   */
  class Handler(
    val initUrl: String,
  ) : InstanceKeeper.Instance

  data class Model(
    val browserViewState: BrowserViewState,
    val toolbarState: BrowserToolbarState = BrowserToolbarState(),
  )

  sealed interface Action : RootComponent.Child.Action {
    object Back : Action
    object AddTab : Action
    object History : Action
//    object HideCustomView : Action

    class Load(val url: String) : Action
    class ReceivedIcon(val icon: Bitmap?) : Action
    class CreateWindow(val view: NativeWebView?, val url: String) : Action

    class PageStarted(val url: String?, val title: String?) : Action
    class PageFinished(val url: String?, val title: String?) : Action
    class PageCommitVisible(val url: String?, val title: String?) : Action
  }
}