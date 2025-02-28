package com.next.level.solutions.calculator.fb.mp

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.graphics.Bitmap
import android.net.Uri
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
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import androidx.room.RoomDatabase
import com.arkivanov.decompose.defaultComponentContext
import com.next.level.solutions.calculator.fb.mp.data.database.MyDatabase
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.AdsManager
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.AdsManagerImpl
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.app_open.AdsAppOpenImpl
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.inter.AdsInterImpl
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.nativ.AdsNativeImpl
import com.next.level.solutions.calculator.fb.mp.expect.AppEvent
import com.next.level.solutions.calculator.fb.mp.file.hider.FileHiderImpl
import com.next.level.solutions.calculator.fb.mp.ui.screen.language.changer.ChangerLocalStore
import com.next.level.solutions.calculator.fb.mp.ui.screen.language.changer.LanguageChangerImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okio.FileSystem
import java.io.File

class MainActivity : ComponentActivity() {
  companion object {
    var adsManager: AdsManager? = null
    var languageChanger: Lazy<LanguageChangerImpl>? = null
    var producePath: ((String) -> String)? = null
    var externalStoragePermissionGranted: (() -> Boolean)? = null
    var requestExternalStoragePermission: (() -> Unit)? = null
    var collapse: (() -> Unit)? = null
    var systemBars: ((Boolean) -> Unit)? = null
    var appEventListeners: ((AppEvent) -> Unit)? = null
    var roomDatabase: (() -> RoomDatabase.Builder<MyDatabase>)? = null
    var fileHider: (() -> FileHiderImpl)? = null
    var showCustomView: ((View?) -> Unit)? = null
    var hideCustomView: (() -> Unit)? = null
    var saveBitmapToCache: ((Bitmap?, String, Int) -> Unit)? = null
  }

  private val launcher: ActivityResultLauncher<Array<String>> = registerForActivityResult(
    contract = RequestMultiplePermissions(),
    callback = {},
  )

  override fun attachBaseContext(newBase: Context?) {
    val newBaseContext = newBase?.let { context ->
      val resources = context.resources
      val configuration = resources.configuration

      val store = ChangerLocalStore(context.getSharedPreferences("Changer", Context.MODE_PRIVATE))
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
    installSplashScreen()
    super.onCreate(savedInstanceState)

    init()
    iniExpect()

    val componentContext = defaultComponentContext()

    setContent {
      App(
        componentContext = componentContext,
      )
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    adsManager?.native?.destroy()
    adsManager = null
  }

  private fun init() {
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
  }

  private fun iniExpect() {producePath = { filesDir.resolve(it).absolutePath }
    externalStoragePermissionGranted = {
      when {
        Build.VERSION.SDK_INT >= 30 -> Environment.isExternalStorageManager()
        else -> hasPermission(WRITE_EXTERNAL_STORAGE) && hasPermission(READ_EXTERNAL_STORAGE)
      }
    }

    languageChanger = lazy {
      LanguageChangerImpl(
        activity = this,
        store = ChangerLocalStore(getSharedPreferences("Changer", Context.MODE_PRIVATE))
      )
    }

    requestExternalStoragePermission = {
      lifecycleScope.launch(Dispatchers.Main) {
        while (externalStoragePermissionGranted?.invoke() == false) {
          delay(300)
        }

        val intent = Intent(this@MainActivity, MainActivity::class.java)

        intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        startActivity(intent)
      }

      if (Build.VERSION.SDK_INT >= 30) {
        try {
          val intent = Intent(
            Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
            Uri.parse("package:$packageName"),
          )

          startActivity(intent)
        } catch (e: Exception) {
          val intent = Intent(
            Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION,
          )
          intent.addFlags(FLAG_ACTIVITY_NEW_TASK)

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

    systemBars = {
      systemBars(it)
    }

    collapse = {
      startActivity(
        Intent(Intent.ACTION_MAIN).apply {
          addCategory(Intent.CATEGORY_HOME)
          flags = FLAG_ACTIVITY_NEW_TASK
        }
      )
    }

    saveBitmapToCache = { icon, name, quality ->
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

    fileHider = {
      FileHiderImpl(this)
    }

    var fullScreenView: View? = null

    showCustomView = {
      fullScreenView = it

      it?.let { view ->
        val rootView = window?.decorView as? ViewGroup
        rootView?.addView(view, FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT))

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
        systemBars(false)

        view.requestFocus()
      }
    }

    hideCustomView = {
      val rootView = window?.decorView as? ViewGroup
      rootView?.removeView(fullScreenView)
      fullScreenView = null

      systemBars(true)
      @SuppressLint("SourceLockedOrientationActivity")
      requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    roomDatabase = {
      val dbFile = applicationContext.getDatabasePath("my_room.db")

      Room.databaseBuilder<MyDatabase>(
        context = applicationContext,
        name = dbFile.absolutePath
      )
    }

    adsManager = AdsManagerImpl(
      activity = this,
      inter = AdsInterImpl(this),
      native = AdsNativeImpl(this),
      appOpen = AdsAppOpenImpl(this),
    )

    adsManager?.init {
      // todo ?
    }
  }

  private fun Activity.systemBars(show: Boolean) {
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

  private fun Context.hasPermission(permissions: String): Boolean {
    return checkSelfPermission(this, permissions) == PERMISSION_GRANTED
  }
}