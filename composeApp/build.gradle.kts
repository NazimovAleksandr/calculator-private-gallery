import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.compose.multiplatform)

  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.kotlin.compose.compiler)
  alias(libs.plugins.kotlin.serialization)

  alias(libs.plugins.ksp)
}

kotlin {
  androidTarget {
    compilerOptions {
      jvmTarget.set(JvmTarget.JVM_11)
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
      implementation(libs.bundles.admob)

      // mathParser
      implementation(libs.math.parser)
    }

    commonMain.dependencies {
      implementation(compose.runtime)
      implementation(compose.foundation)
      implementation(compose.material3)
      implementation(compose.ui)
      implementation(compose.components.resources)
      implementation(compose.components.uiToolingPreview)

      implementation(libs.androidx.lifecycle.viewmodel)
      implementation(libs.androidx.lifecycle.runtime.compose)

      // kotlinx
      implementation(libs.kotlinx.collections)
      implementation(libs.kotlinx.serialization)

      // decompose
      implementation(libs.decompose)
      implementation(libs.decompose.extensions.compose)
      implementation(libs.decompose.lifecycle.coroutines)

      // koin
      implementation(libs.koin.core)
      implementation(libs.koin.compose)
      api(libs.koin.annotations)

      // datastore
      implementation(libs.datastore)
    }

    // KSP Common sourceSet
    sourceSets.named("commonMain").configure {
      kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
    }
  }
}

android {
  namespace = "com.next.level.solutions.calculator.fb.mp"
  compileSdk = libs.versions.android.compileSdk.get().toInt()

  defaultConfig {
    applicationId = "com.next.level.solutions.calculator.fb.mp.demo"
    minSdk = libs.versions.android.minSdk.get().toInt()
    targetSdk = libs.versions.android.targetSdk.get().toInt()
    versionCode = 1
    versionName = "1.0"

    manifestPlaceholders.apply {
      set("resizeableActivity", false)
    }
  }
  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
  buildTypes {
    getByName("release") {
      isMinifyEnabled = false
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
}

dependencies {
  // KSP Tasks
  add("kspCommonMainMetadata", libs.koin.ksp.compiler)
  add("kspAndroid", libs.koin.ksp.compiler)
  add("kspIosX64", libs.koin.ksp.compiler)
  add("kspIosArm64", libs.koin.ksp.compiler)
  add("kspIosSimulatorArm64", libs.koin.ksp.compiler)

  implementation(libs.androidx.appcompat)
  debugImplementation(compose.uiTooling)
}

// Trigger Common Metadata Generation from Native tasks
project.tasks.withType(KotlinCompilationTask::class.java).configureEach {
  if (name != "kspCommonMainKotlinMetadata") {
    dependsOn("kspCommonMainKotlinMetadata")
  }
}