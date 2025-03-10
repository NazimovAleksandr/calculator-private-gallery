package com.next.level.solutions.calculator.fb.mp.ui.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.AdsManager
import com.next.level.solutions.calculator.fb.mp.expect.PlatformExp
import com.next.level.solutions.calculator.fb.mp.extensions.core.launchMain
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.PickerType
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.PickerMode
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent.Configuration
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent.DialogConfiguration
import com.next.level.solutions.calculator.fb.mp.ui.root.browser
import com.next.level.solutions.calculator.fb.mp.ui.root.hiddenFiles
import com.next.level.solutions.calculator.fb.mp.ui.root.needAccessDialog
import com.next.level.solutions.calculator.fb.mp.ui.root.settings

@Stable
class HomeComponent(
  componentContext: ComponentContext,
  private val adsManager: AdsManager,
  private val navigation: StackNavigation<Configuration>,
  private val dialogNavigation: SlotNavigation<DialogConfiguration>,
) : RootComponent.Child(adsManager), ComponentContext by componentContext {

  init {
    val root = instanceKeeper.get("RootComponent") as? RootComponent
    root?.action(RootComponent.Action.ActivateCollapseSecurity)
  }

  @Composable
  override fun content() {
    HomeContent(component = this)
  }

  override fun action(action: RootComponent.Child.Action) {
    launchMain {
      action.doSomething()
    }
  }

  private fun RootComponent.Child.Action.doSomething() {
    checkExternalStorage() ?: return

    when (this) {
      is Action.Ad -> adsManager.inter.show {}
//      is Action.SetAdState -> adState = event.state
//      is Action.OnStart -> checkFiles(event.context)
      is Action.Settings -> navigation.pushNew(Configuration.settings())

      else -> navigateOrShowInter()
    }
  }

  private fun RootComponent.Child.Action.checkExternalStorage(): Boolean? {
    val result = when (this) {
      is Action.Trash -> PlatformExp.externalStoragePermissionGranted()
      is Action.Photos -> PlatformExp.externalStoragePermissionGranted()
      is Action.Videos -> PlatformExp.externalStoragePermissionGranted()
      is Action.Files -> PlatformExp.externalStoragePermissionGranted()

      else -> true
    }

    return when (result) {
      true -> true

      else -> {
//        analytics.home.mediaPermissionRequest()
        dialogNavigation.activate(DialogConfiguration.needAccessDialog(PlatformExp::requestExternalStoragePermission))
        null
      }
    }
  }

  private fun RootComponent.Child.Action.navigateOrShowInter() {
    when (this) {
      is Action.Trash -> PickerType.Trash.navigateOrShowInter()
      is Action.Photos -> PickerType.Photo.navigateOrShowInter()
      is Action.Videos -> PickerType.Video.navigateOrShowInter()
      is Action.Files -> PickerType.File.navigateOrShowInter()
      is Action.Notes -> PickerType.Note.navigateOrShowInter()
      is Action.Browser -> navigateOrShowInter()
    }
  }

  private fun PickerType.navigateOrShowInter() {
    val interOffCallback = {
      when (this) {
        PickerType.Trash,
        PickerType.Photo,
        PickerType.Video,
          -> navigation.pushNew(
          Configuration.hiddenFiles(
            fileType = this,
            viewType = PickerMode.Gallery
          )
        )

        PickerType.File,
        PickerType.Note,
          -> navigation.pushNew(
          Configuration.hiddenFiles(
            fileType = this,
            viewType = PickerMode.Folder
          )
        )
      }
    }

    when {
//      trash() -> interOffCallback()
//      interIsEnabledByFirstClick() && showInterByFirstClick() -> showInter()
//      showInterByNClick() -> showInter()
      else -> interOffCallback()
    }
  }

  private fun Action.Browser.navigateOrShowInter() {
    toString()

    val interOffCallback = {
      navigation.pushNew(Configuration.browser())
    }

    when {
//      interIsEnabledByFirstClick() && showInterByFirstClick() -> showInter()
//      showInterByNClick() -> showInter()
      else -> interOffCallback()
    }
  }

  /**
   * Component contract - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
   */
  class Handler : InstanceKeeper.Instance

  sealed interface Action : RootComponent.Child.Action {
    object Settings : Action
    object Browser : Action
    object Ad : Action
    object Notes : Action
    object Trash : Action
    object Photos : Action
    object Videos : Action
    object Files : Action
//    object OnStart : Action
//    class SetAdState(val state: AdState) : Action
  }
}