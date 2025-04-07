package com.next.level.solutions.calculator.fb.mp

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.content.res.Configuration
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.LocaleList
import android.provider.Settings
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.WindowManager
import android.view.WindowManager.LayoutParams.FLAG_SECURE
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
import androidx.lifecycle.lifecycleScope
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.defaultComponentContext
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.AdsManager
import com.next.level.solutions.calculator.fb.mp.entity.ui.FileDataUI
import com.next.level.solutions.calculator.fb.mp.ui.screen.language.changer.ChangerLocalStore
import com.next.level.solutions.calculator.fb.mp.ui.screen.language.changer.LanguageChanger
import com.next.level.solutions.calculator.fb.mp.utils.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okio.FileSystem
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.compose.KoinApplication
import org.koin.core.logger.Level
import java.io.File
import java.net.URLConnection

class MainActivity : ComponentActivity() {
  companion object {
    var expect: Lazy<Expect>? = null
  }

  private val languageChanger: LanguageChanger by inject()

  private var fullScreenView: View? = null

  private val launcher: ActivityResultLauncher<Array<String>> = registerForActivityResult(
    contract = RequestMultiplePermissions(),
    callback = {},
  )

  override fun attachBaseContext(newBase: Context?) {
    val newBaseContext = newBase?.let { context ->
      val resources = context.resources
      val configuration = resources.configuration

      val store = ChangerLocalStore(context.getSharedPreferences("Changer", MODE_PRIVATE))
      val localeToSwitchTo = store.getLocale()

      configuration.setLocale(localeToSwitchTo)
      val localeList = LocaleList(localeToSwitchTo)
      LocaleList.setDefault(localeList)
      configuration.setLocales(localeList)

      ContextWrapper(context.createConfigurationContext(configuration))
    }

    super.attachBaseContext(newBaseContext)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    prepareWindow()
    installSplashScreen()

    super.onCreate(savedInstanceState)

    val componentContext: DefaultComponentContext = defaultComponentContext()

    setContent {
      KoinApplication(
        application = {
          androidLogger(level = if (BuildConfig.DEBUG) Level.DEBUG else Level.NONE)
          androidContext(androidContext = this@MainActivity)
          appModules()
        },
        content = {
          App(
            componentContext = componentContext,
          )
        },
      )
    }
  }

  override fun onStart() {
    super.onStart()
    initExpect()
  }

  override fun onStop() {
    super.onStop()
    expect = null
  }

  override fun onConfigurationChanged(newConfig: Configuration) {
    super.onConfigurationChanged(newConfig)

    languageChanger.updateLocale()

    when (newConfig.orientation) {
      ORIENTATION_LANDSCAPE -> expect?.value?.screenOrientationListener?.invoke(true)
      else -> expect?.value?.screenOrientationListener?.invoke(false)
    }
  }

  private fun prepareWindow() {
    val color = when (true) {
      true -> Color.Black.copy(alpha = 0.01f)
      else -> Color.White.copy(alpha = 0.01f)
    }.toArgb()

    if (!BuildConfig.DEBUG) {
      window.setFlags(FLAG_SECURE, FLAG_SECURE)
    }

    enableEdgeToEdge(
      statusBarStyle = SystemBarStyle.auto(lightScrim = color, darkScrim = color),
      navigationBarStyle = SystemBarStyle.auto(lightScrim = color, darkScrim = color),
    )

    screenOrientationPortrait()
  }

  private fun initExpect() {
    expect = lazy {
      Expect(
        externalStoragePermissionGranted = ::externalStoragePermissionGranted,
        requestExternalStoragePermission = ::requestExternalStoragePermission,
        saveBitmapToCache = ::saveBitmapToCache,
        showCustomView = ::showCustomView,
        hideCustomView = ::hideCustomView,
        openMarket = ::openMarket,
        systemBars = ::systemBars,
        shareLink = ::shareLink,
        shareApp = ::shareApp,
        collapse = ::collapse,
        openFile = ::openFile,
        screenOrientationFullSensor = ::screenOrientationFullSensor,
        screenOrientationPortrait = ::screenOrientationPortrait,
        screenOrientationSensorLandscape = ::screenOrientationSensorLandscape,
      )
    }
  }

  private fun collapse() {
    startActivityTry(
      Intent(Intent.ACTION_MAIN).apply {
        addCategory(Intent.CATEGORY_HOME)
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
      }
    )
  }

  private fun openFile(fileDataUI: FileDataUI) {
    try {
      val file = File(
        /* pathname = */ fileDataUI.hiddenPath ?: fileDataUI.path,
      )

      val mimeType = URLConnection.guessContentTypeFromName(
        /* fname = */ file.absolutePath,
      )

      val uri = FileProvider.getUriForFile(
        /* context = */ this,
        /* authority = */ applicationContext.packageName + ".provider",
        /* file = */ file,
      )

      val intent = Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(uri, mimeType)
        setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
      }

      startActivity(intent)
    } catch (e: ActivityNotFoundException) {
      Toast.makeText(this, getString(R.string.app_not_found), Toast.LENGTH_SHORT).show()
      Logger.e("TAG", "error = $e")
    }
  }

  private fun shareLink(title: String?, link: String) {
    val intent = Intent().apply {
      action = Intent.ACTION_SEND
      type = "text/*"

      title?.let { putExtra(Intent.EXTRA_SUBJECT, title) }
      putExtra(Intent.EXTRA_TEXT, link)
    }

    val chooserTitle = getString(R.string.share)
    val shareIntent = Intent.createChooser(intent, chooserTitle)

    startActivityTry(shareIntent)
  }

  private fun shareApp() {
    val storeLink = "https://play.google.com/store/apps/details?id="

    val intent = Intent().apply {
      action = Intent.ACTION_SEND
      type = "text/plain"

      putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))

      putExtra(
        Intent.EXTRA_TEXT,
        """
          ${getString(R.string.let_me_recommend)}

          $storeLink$packageName
        """.trimIndent()
      )
    }

    val chooserTitle = getString(R.string.share)
    val shareIntent = Intent.createChooser(intent, chooserTitle)

    startActivityTry(shareIntent)
  }

  private fun openMarket() {
    val storeLink = "https://play.google.com/store/apps/details?id="

    val goToMarket = Intent().apply {
      action = Intent.ACTION_VIEW
      data = "$storeLink$packageName".toUri()
      setPackage("com.android.vending")
    }

    try {
      startActivity(goToMarket)
    } catch (_: ActivityNotFoundException) {
      try {
        startActivity(
          Intent().apply {
            action = Intent.ACTION_VIEW
            data = "$storeLink$packageName".toUri()
          }
        )
      } catch (_: Exception) {
      }
    }
  }

  private fun systemBars(show: Boolean) {
    val windowInsetsController: WindowInsetsControllerCompat = WindowCompat.getInsetsController(
      /* window = */ window,
      /* view = */ window.decorView,
    )

    windowInsetsController.systemBarsBehavior = BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

    when (show) {
      true -> windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
      else -> windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
      window.attributes.layoutInDisplayCutoutMode = when (show) {
        true -> WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT
        else -> WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
      }
    }
  }

  private fun showCustomView(view: View?) {
    fullScreenView = view

    view?.let {
      val rootView = window?.decorView as? ViewGroup
      rootView?.addView(view, FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT))

      requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
      systemBars(false)

      view.requestFocus()
    }
  }

  private fun hideCustomView() {
    val rootView = window?.decorView as? ViewGroup
    rootView?.removeView(fullScreenView)
    fullScreenView = null

    systemBars(true)
    @SuppressLint("SourceLockedOrientationActivity")
    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
  }

  private fun saveBitmapToCache(icon: Bitmap?, name: String, quality: Int) {
    icon?.let {
      val folder = File(FileSystem.SYSTEM_TEMPORARY_DIRECTORY.toFile(), "image_cache")
      if (!folder.exists()) folder.mkdir()

      val file = File(folder, "$name.png")
      if (!file.exists()) folder.mkdir()

      file.outputStream().use { out ->
        it.compress(Bitmap.CompressFormat.PNG, quality, out)
        out.flush()
      }
    }
  }

  private fun externalStoragePermissionGranted(): Boolean {
    return when {
      Build.VERSION.SDK_INT >= 30 -> Environment.isExternalStorageManager()
      else -> hasPermission(WRITE_EXTERNAL_STORAGE) && hasPermission(READ_EXTERNAL_STORAGE)
    }
  }

  private fun requestExternalStoragePermission() {
    lifecycleScope.launch(Dispatchers.Main) {
      while (!externalStoragePermissionGranted()) {
        delay(300)
      }

      val intent = Intent(this@MainActivity, MainActivity::class.java)

      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
      intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

      startActivityTry(intent)
    }

    if (Build.VERSION.SDK_INT >= 30) {
      try {
        val intent = Intent(
          Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
          "package:$packageName".toUri(),
        )

        startActivity(intent)
      } catch (_: Exception) {
        val intent = Intent(
          Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION,
        )
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        startActivity(intent)
      }
    } else {
      launcher.launch(
        arrayOf(
          READ_EXTERNAL_STORAGE,
          WRITE_EXTERNAL_STORAGE,
        )
      )
    }
  }

  private fun Context.hasPermission(permissions: String): Boolean {
    return checkSelfPermission(this, permissions) == PERMISSION_GRANTED
  }

  private fun Context.startActivityTry(intent: Intent, onError: (() -> Unit)? = null) {
    try {
      startActivity(intent)
    } catch (_: Exception) {
      onError?.invoke()
    }
  }

  private fun screenOrientationFullSensor() {
    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
  }

  private fun screenOrientationPortrait() {
    @SuppressLint("SourceLockedOrientationActivity")
    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
  }

  private fun screenOrientationSensorLandscape() {
    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
  }

  class Expect(
    val externalStoragePermissionGranted: () -> Boolean,
    val requestExternalStoragePermission: () -> Unit,
    val collapse: () -> Unit,
    val openMarket: () -> Unit,
    val shareApp: () -> Unit,
    val systemBars: (Boolean) -> Unit,
    val showCustomView: (View?) -> Unit,
    val hideCustomView: () -> Unit,
    val saveBitmapToCache: (Bitmap?, String, Int) -> Unit,
    val shareLink: (String?, String) -> Unit,
    val openFile: (fileDataUI: FileDataUI) -> Unit,
    val screenOrientationFullSensor: () -> Unit,
    val screenOrientationPortrait: () -> Unit,
    val screenOrientationSensorLandscape: () -> Unit,
    var screenOrientationListener: ((Boolean) -> Unit)? = null,
  )
}