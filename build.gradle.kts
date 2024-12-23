buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        // Plugin Android Gradle dan Kotlin tetap digunakan
        classpath("com.android.tools.build:gradle:8.7.2")  // Versi plugin Gradle Android
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0")  // Versi plugin Kotlin
    }
}

plugins {
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.android.application") version "8.7.2" apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
}
