import org.jetbrains.kotlin.storage.CacheResetOnProcessCanceled.enabled

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")  // Pastikan plugin Parcelize ditambahkan
}

android {
    namespace = "com.example.ecomama"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.ecomama"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
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

    buildFeatures{
        viewBinding = true
    }

    // Kotlin options
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.firebase.auth.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.firebase.database.ktx)
    implementation("androidx.databinding:dataBinding-runtime:7.4.2")  // Pastikan ini ada jika menggunakan DataBinding
}
