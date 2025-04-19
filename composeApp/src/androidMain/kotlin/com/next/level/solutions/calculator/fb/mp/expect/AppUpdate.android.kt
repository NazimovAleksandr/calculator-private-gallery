package com.next.level.solutions.calculator.fb.mp.expect

import android.content.Context
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import com.google.android.gms.tasks.Task
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability

actual class AppUpdate(
  context: Context,
) {
  private val appUpdateManager = AppUpdateManagerFactory.create(context)
  private var intentSender: ActivityResultLauncher<IntentSenderRequest>? = null
  private var resultListener: ((Boolean) -> Unit)? = null

  actual fun checkAppUpdate(type: String, result: (Boolean) -> Unit) {
    resultListener = result

    when (type) {
      "IMMEDIATE" -> checkAppUpdate(AppUpdateType.IMMEDIATE)
      "FLEXIBLE" -> checkAppUpdate(AppUpdateType.FLEXIBLE)
    }
  }

  fun setIntentSender(launcher: ActivityResultLauncher<IntentSenderRequest>) {
    intentSender = launcher
  }

  fun result(result: Boolean) {
    resultListener?.let { it(result) }
    resultListener = null
  }

  private fun checkAppUpdate(@AppUpdateType updateType: Int) {
    val intentSender: ActivityResultLauncher<IntentSenderRequest> = intentSender ?: return

    val appUpdateInfoTask: Task<AppUpdateInfo?> = appUpdateManager.appUpdateInfo

    appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
      if (appUpdateInfo?.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
        && appUpdateInfo.isUpdateTypeAllowed(updateType)
      ) {
        appUpdateManager.startUpdateFlowForResult(
          appUpdateInfo,
          intentSender,
          AppUpdateOptions.newBuilder(updateType).build(),
        )
      }
    }
  }
}