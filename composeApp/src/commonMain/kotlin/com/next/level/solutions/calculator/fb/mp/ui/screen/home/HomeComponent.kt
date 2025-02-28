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
import com.next.level.solutions.calculator.fb.mp.expect.externalStoragePermissionGranted
import com.next.level.solutions.calculator.fb.mp.expect.requestExternalStoragePermission
import com.next.level.solutions.calculator.fb.mp.extensions.core.launchMain
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.FilePickerFileType
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.FilePickerViewType
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

  override fun content(): @Composable () -> Unit = {
    HomeContent(component = this)
  }

  override fun action(action: RootComponent.Child.Action) {
    launchMain {
      action.doSomething()?.let {
        action(it)
      }
    }
  }

  private fun RootComponent.Child.Action.doSomething(): Action? {
    checkExternalStorage() ?: return null

    when (this) {
      is Action.Ad -> adsManager.inter.show {}
//      is Action.SetAdState -> adState = event.state
//      is Action.OnStart -> checkFiles(event.context)
      is Action.Settings -> navigation.pushNew(Configuration.settings())

      else -> navigateOrShowInter()
    }

    return null
  }

  private fun RootComponent.Child.Action.checkExternalStorage(): Boolean? {
    val result = when (this) {
      is Action.Trash -> externalStoragePermissionGranted()
      is Action.Photos -> externalStoragePermissionGranted()
      is Action.Videos -> externalStoragePermissionGranted()
      is Action.Files -> externalStoragePermissionGranted()

      else -> true
    }

    return when (result) {
      true -> true

      else -> {
//        analytics.home.mediaPermissionRequest()
        dialogNavigation.activate(DialogConfiguration.needAccessDialog(::requestExternalStoragePermission))
        null
      }
    }
  }

  private fun RootComponent.Child.Action.navigateOrShowInter() {
    when (this) {
      is Action.Trash -> FilePickerFileType.Trash.navigateOrShowInter()
      is Action.Photos -> FilePickerFileType.Photo.navigateOrShowInter()
      is Action.Videos -> FilePickerFileType.Video.navigateOrShowInter()
      is Action.Files -> FilePickerFileType.File.navigateOrShowInter()
      is Action.Notes -> FilePickerFileType.Note.navigateOrShowInter()
      is Action.Browser -> navigateOrShowInter()
    }
  }

  private fun FilePickerFileType.navigateOrShowInter() {
    val interOffCallback = {
      when (this) {
        FilePickerFileType.Trash,
        FilePickerFileType.Photo,
        FilePickerFileType.Video,
          -> navigation.pushNew(
          Configuration.hiddenFiles(
            fileType = this,
            viewType = FilePickerViewType.Gallery
          )
        )

        FilePickerFileType.File,
        FilePickerFileType.Note,
          -> navigation.pushNew(
          Configuration.hiddenFiles(
            fileType = this,
            viewType = FilePickerViewType.Folder
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