plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.compose.compiler) apply false
    kotlin("jvm") version "2.0.20"
    kotlin("plugin.serialization") version "2.0.20"
}