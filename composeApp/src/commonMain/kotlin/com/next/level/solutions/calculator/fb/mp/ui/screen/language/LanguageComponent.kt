package com.next.level.solutions.calculator.fb.mp.ui.screen.language

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.next.level.solutions.calculator.fb.mp.data.datastore.AppDatastore
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.AdsManager
import com.next.level.solutions.calculator.fb.mp.extensions.core.launch
import com.next.level.solutions.calculator.fb.mp.extensions.core.launchMain
import com.next.level.solutions.calculator.fb.mp.ui.root.HiddenFilesConfiguration.hiddenFiles
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent
import com.next.level.solutions.calculator.fb.mp.ui.screen.language.changer.LanguageChanger
import com.next.level.solutions.calculator.fb.mp.ui.screen.language.changer.getDefaultLocaleLanguageCode
import com.next.level.solutions.calculator.fb.mp.ui.screen.language.model.LanguageModel
import com.next.level.solutions.calculator.fb.mp.utils.Logger
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

class LanguageComponent(
  componentContext: ComponentContext,
  adsManager: AdsManager,
  private val appDatastore: AppDatastore,
  private val languageChanger: LanguageChanger,
  private val navigation: StackNavigation<RootComponent.Configuration>,
) : RootComponent.Child(adsManager), ComponentContext by componentContext {

  private val handler: Handler = instanceKeeper.get(key = componentContext) as Handler

  private val languageModels: MutableList<LanguageModel> = mutableListOf(
    LanguageModel(name = "English", code = "en"),
    LanguageModel(name = "Español", code = "es"),
    LanguageModel(name = "Indonesia", code = "in"),
    LanguageModel(name = "Português", code = "pt"),
    LanguageModel(name = "Português (BR)", code = "pt", country = "-BR"),
//    LanguageModel(name = "हिंदी", code = "hi"),
//    LanguageModel(name = "ગુજરાતી", code = "gu"),
//    LanguageModel(name = "ಕನ್ನಡ", code = "kn"),
//    LanguageModel(name = "தமிழ்", code = "ta"),
//    LanguageModel(name = "తెలుగు", code = "te"),
//    LanguageModel(name = "تعيين الرمز السري", code = "ar"),
//    LanguageModel(name = "اردو", code = "ur"),
//    LanguageModel(name = "বাংলা", code = "bn"),
//    LanguageModel(name = "Deutsch", code = "de"),
//    LanguageModel(name = "Norsk", code = "nb"),
//    LanguageModel(name = "Русский", code = "ru"),
//    LanguageModel(name = "Italiano", code = "it"),
//    LanguageModel(name = "Français", code = "fr"),
//    LanguageModel(name = "Filipino", code = "fil"),
//    LanguageModel(name = "Svenska", code = "sv"),
//    LanguageModel(name = "中國人", code = "zh"),
//    LanguageModel(name = "한국인", code = "ko"),
//    LanguageModel(name = "日本語", code = "ja"),
  )

  private val _model: MutableValue<Model> = MutableValue(initialState())
  val model: Value<Model> = _model

  init {
    Logger.w("TAG_LANGUAGE", "LanguageComponent init = $this")
  }

  override fun content(): @Composable () -> Unit = {
    LanguageContent(component = this)
  }

  override fun action(action: RootComponent.Child.Action) {
    action.updateModel()

    launch {
      action.doSomething()?.let {
        action(it)
      }
    }
  }

  private fun initialState(): Model {
//    analytics.language.languageSelectionOpen()

    val defaultLocaleLanguage = getDefaultLocaleLanguageCode()
    val languages = languages()

    val selected: LanguageModel = languages
      .flatten()
      .firstOrNull { it?.code == defaultLocaleLanguage }
      ?: languageModels[0]

    return Model(
      languages = languages,
      selected = selected,
      activateCollapseSecurity = handler.changeMode,
    )
  }

  private fun RootComponent.Child.Action.updateModel() {
    when (this) {
      is Action.ApplyLanguage -> update()
    }
  }

  private suspend fun RootComponent.Child.Action.doSomething(): Action? {
    when (this) {
      is Action.ApplyLanguage -> updateLocale()
      is Action.Done -> done()
    }

    return null
  }

  private fun Action.ApplyLanguage.update() {
    _model.update { it.copy(selected = value) }
  }

  private suspend fun Action.ApplyLanguage.updateLocale() {
    languageChanger.updateLocale(languageModel = value)

    appDatastore.languageName(value = value.name)
    appDatastore.languageModel(value = value)
  }

  private suspend fun done() {
//    analytics.language.languageSet()

    appDatastore.languageState(true)

    launchMain {
      when (handler.changeMode) {
        true -> navigation.pop()
        else -> navigation.replaceCurrent(RootComponent.Configuration.hiddenFiles()) // todo calculator
      }
    }
  }

  private fun languages(): ImmutableList<List<LanguageModel?>> {
    return languageModels
      .let { list ->
        val language = getDefaultLocaleLanguageCode()

        val currentLocal = list.firstOrNull { pair ->
          pair.code == language
        }

        when {
          currentLocal != null -> {
            list.remove(currentLocal)
            list.add(1, currentLocal)
          }
        }

        val resultList: MutableList<List<LanguageModel?>> = mutableListOf()

        for (i in list.indices step 2) {
          resultList.add(
            listOf(
              list.getOrNull(i),
              list.getOrNull(i + 1),
            )
          )
        }

        resultList
      }
      .toImmutableList()
  }

  /**
   * Store contract - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
   */
  companion object {
    val INSTANCE_KEY: String = this::class.toString()
  }

  class Handler(val changeMode: Boolean = false) : InstanceKeeper.Instance

  data class Model(
    val languages: ImmutableList<List<LanguageModel?>>,
    val selected: LanguageModel,
    val activateCollapseSecurity: Boolean,
  )

  sealed interface Action : RootComponent.Child.Action {
    data object Done : Action
    data class ApplyLanguage(val value: LanguageModel) : Action
  }
}