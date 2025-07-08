// Top-level build file
plugins {
    // Android Gradle Plugin
    id("com.android.application") version "8.11.0" apply false

    // Kotlin Plugin
    id("org.jetbrains.kotlin.android") version "2.1.20" apply false

    // KSP Plugin - Η έκδοση πρέπει να ταιριάζει με της Kotlin
    id("com.google.devtools.ksp") version "2.1.20-1.0.31" apply false

    // Hilt Plugin
    id("com.google.dagger.hilt.android") version "2.56.2" apply false
}