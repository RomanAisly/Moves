import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)

}

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localPropertiesFile.inputStream().use {
        localProperties.load(it)
    }
}

android {
    namespace = "com.films"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.films"
        minSdk = 29
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("String", "API_KEY", "\"${localProperties.getProperty("API_KEY")}\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
        }
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    // Compose- BOM
    implementation(platform(libs.androidx.compose.bom))

    // UI
    implementation(libs.bundles.androidx.compose)
    implementation(libs.lottie.compose)
    implementation(libs.core)

    // Network, Coroutines
    implementation(libs.bundles.koin)
    implementation(libs.bundles.ktor)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.coroutines.android)

    // Navigation, Coil, DataStore и WorkManager
    implementation(libs.androidx.navigation.compose)
    implementation(libs.coil.compose)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.datastore.preferences)

    // Room
    implementation(libs.bundles.room)
    ksp(libs.room.compiler)

    //Security
    implementation(libs.androidx.security.crypto.ktx)

    // Debug
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // Unit-tests
    testImplementation(libs.junit)

    // Android-tests
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom)) // BOM нужен и для тестов
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

}