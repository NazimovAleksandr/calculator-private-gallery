package com.next.level.solutions.calculator.fb.mp.expect

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.next.level.solutions.calculator.fb.mp.MainActivity
import com.next.level.solutions.calculator.fb.mp.ui.screen.language.changer.LanguageChanger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

actual class AppEventListener(
  activity: Activity,
  private val languageChanger: LanguageChanger,
) : Application.ActivityLifecycleCallbacks {
  private var _callback: ((AppEvent) -> Unit)? = null

  private var topActivity: Activity? = null
  private var showAppOpenAd: Boolean = false

  init {
    (activity.applicationContext as? Application)?.registerActivityLifecycleCallbacks(this)
  }

  actual fun set(callback: (AppEvent) -> Unit) {
    _callback = callback
  }

  override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    languageChanger.updateLocale()
  }

  override fun onActivityStarted(activity: Activity) {
    topActivity = activity

    if (activity !is MainActivity) return
    if (!showAppOpenAd) return

    showAppOpenAd = false

    activity.lifecycleScope.launch(Dispatchers.Main) {
      delay(500)
      _callback?.invoke(AppEvent.AppOpen)
    }
  }

  override fun onActivityStopped(activity: Activity) {
    if (activity !is MainActivity) return
    if (topActivity !is MainActivity) return

    _callback?.invoke(AppEvent.AppLock)
    showAppOpenAd = true
  }

  override fun onActivityResumed(activity: Activity) {}
  override fun onActivityPaused(activity: Activity) {}
  override fun onActivityDestroyed(activity: Activity) {}
  override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
}