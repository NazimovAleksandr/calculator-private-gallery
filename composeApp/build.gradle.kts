import com.google.devtools.ksp.KspExperimental
import org.gradle.kotlin.dsl.implementation
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

private val demo = true

plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.compose.multiplatform)

  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.kotlin.compose.compiler)
  alias(libs.plugins.kotlin.serialization)

  alias(libs.plugins.google.services)
  alias(libs.plugins.firebase.crashlytics)

  alias(libs.plugins.androidx.room)
  alias(libs.plugins.ksp)
}

kotlin {
  androidTarget {
    compilerOptions {
      jvmTarget.set(JvmTarget.JVM_17)
    }
  }

  listOf(
    iosX64(),
    iosArm64(),
    iosSimulatorArm64()
  ).forEach { iosTarget ->
    iosTarget.binaries.framework {
      baseName = "ComposeApp"
      isStatic = true
    }
  }

  sourceSets {
    androidMain.dependencies {
      implementation(compose.preview)

      implementation(libs.androidx.activity.compose)
      implementation(libs.androidx.splashscreen)
      implementation(libs.androidx.cardview)
      implementation(libs.androidx.media3.exoplayer)
      implementation(libs.androidx.media3.ui)

      implementation(libs.android.app.update)
      implementation(libs.android.app.update.ktx)

      implementation(libs.android.appmetrica)
      implementation(libs.android.billing)
      implementation(libs.android.billing.ktx)

      implementation(libs.koin.android)
      implementation(libs.math.parser)

      implementation(libs.bundles.admob)
    }

    commonMain.dependencies {
      implementation(compose.runtime)
      implementation(compose.foundation)
      implementation(compose.material3)
      implementation(compose.ui)
      implementation(compose.components.resources)
      implementation(compose.components.uiToolingPreview)

      implementation(libs.compose.shadow)

      implementation(libs.androidx.datastore)
      implementation(libs.androidx.room.runtime)
      implementation(libs.androidx.sqlite.bundled)

      implementation(libs.androidx.lifecycle.viewmodel)
      implementation(libs.androidx.lifecycle.runtime.compose)

      // kotlinx
      implementation(libs.kotlinx.coroutines)
      implementation(libs.kotlinx.collections)
      implementation(libs.kotlinx.serialization)

      // decompose
      implementation(libs.decompose)
      implementation(libs.decompose.extensions.compose)
      implementation(libs.decompose.lifecycle.coroutines)

      // koin
      implementation(libs.koin.core)
      implementation(libs.koin.compose)

      implementation(libs.coil3.compose)
      implementation(libs.coil3.video)

      implementation(libs.gson)

      implementation(libs.compottie)
      implementation(libs.webview.multiplatform)
      implementation(libs.ktor.client.core)

      api(libs.gitlive.firebase.crashlytics)
      api(libs.gitlive.firebase.analytics)
      api(libs.gitlive.firebase.config)
    }

    // KSP Common sourceSet
    sourceSets.named("commonMain").configure {
      kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
    }
  }

  targets.configureEach {
    compilations.configureEach {
      compileTaskProvider.get().compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
      }
    }
  }
}

android {
  namespace = "com.next.level.solutions.calculator.fb.mp"
  compileSdk = libs.versions.android.compileSdk.get().toInt()

  signingConfigs {
    create("release") {
      storeFile = file("next-level-solutions.keystore")
      storePassword = "dfkjh522wer294nv"
      keyAlias = "next-level-solutions-alias"
      keyPassword = "dfkjh522wer294nv"
    }
  }

  defaultConfig {
    applicationId = applicationId()
    minSdk = libs.versions.android.minSdk.get().toInt()
    targetSdk = libs.versions.android.targetSdk.get().toInt()

    versionCode = libs.versions.android.versionCode.get().toInt()
    versionName = libs.versions.android.versionName.get() + if (demo) ".demo" else ""

    manifestPlaceholders.apply {
      val appName = when {
        demo -> "@string/app_name_demo"
        else -> "@string/app_name"
      }

      set("appName", appName)
      set("resizeableActivity", false)
      set("admobAppId", if (demo) AdMob.APP_ID_DEMO else AdMob.APP_ID)
    }

    val adMobInterId = if (demo) AdMob.INTER_ID_DEMO else AdMob.INTER_ID
    val adMobNativeId = if (demo) AdMob.NATIVE_ID_DEMO else AdMob.NATIVE_ID
    val adMobAppOpenId = if (demo) AdMob.APP_OPEN_ID_DEMO else AdMob.APP_OPEN_ID

    buildConfigField("String", "ADMOB_INTER_ID", "\"$adMobInterId\"")
    buildConfigField("String", "ADMOB_NATIVE_ID", "\"$adMobNativeId\"")
    buildConfigField("String", "ADMOB_APP_OPEN_ID", "\"$adMobAppOpenId\"")

    val appMetricaApiKey = if (demo) AppMetrica.API_KEY_DEMO else AppMetrica.API_KEY

    buildConfigField("String", "APP_METRICA_API_KEY", "\"$appMetricaApiKey\"")
  }

  buildFeatures {
    compose = true
    buildConfig = true
  }

  buildTypes {
    getByName("release") {
      isMinifyEnabled = true
      isShrinkResources = true
      signingConfig = signingConfigs.getByName("release")
      ndk.debugSymbolLevel = "FULL"
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }

  bundle {
    language {
      enableSplit = false
    }
  }

  compileOptions {
    isCoreLibraryDesugaringEnabled = true
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
}

dependencies {
  coreLibraryDesugaring(libs.desugar.jdk.libs)
  implementation(libs.androidx.appcompat)
  debugImplementation(compose.uiTooling)

  ksp(libs.koin.ksp.compiler)
  ksp(libs.androidx.room.compiler)
}

ksp {
  @OptIn(KspExperimental::class)
  useKsp2 = true
}

room {
  schemaDirectory("$projectDir/schemas")
}

fun applicationId(): String {
  val appId = "com.next.level.solutions.calculator.fb.mp"
  return if (demo) "$appId.demo" else appId
}

object AdMob {
  const val APP_ID = "ca-app-pub-3553146986356490~5718920444"
  const val INTER_ID = "ca-app-pub-3553146986356490/9513635734"
  const val NATIVE_ID = "ca-app-pub-3553146986356490/7019860998"
  const val APP_OPEN_ID = "ca-app-pub-3553146986356490/9729258159"

  const val APP_ID_DEMO = "ca-app-pub-3940256099942544~3347511713"
  const val INTER_ID_DEMO = "ca-app-pub-3940256099942544/1033173712"
  const val NATIVE_ID_DEMO = "ca-app-pub-3940256099942544/2247696110"
  const val APP_OPEN_ID_DEMO = "ca-app-pub-3940256099942544/9257395921"
}

object AppMetrica {
  const val API_KEY = "b2389ce4-66de-4e04-aa10-82d2bdd81d51"
  const val API_KEY_DEMO = "d64a9879-2806-4f7d-aae9-aadb0fc631f3"
}