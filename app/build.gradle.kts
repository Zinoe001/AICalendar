import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.serialization)
    alias(libs.plugins.hilt)
    alias(libs.plugins.roomcompiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.google.service)
}


android {
    namespace = "com.purple.aicalendar"
    compileSdk  = 36

    defaultConfig {
        applicationId = "com.purple.aicalendar"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("boolean", "DEBUG_LEVEL", "true")
        buildConfigField("String", "BASE_URL", "\"https://firestore.googleapis.com/\"")
    }

    room {
        schemaDirectory(project.file("src/main/schemas").absolutePath) // KSP uses this path automatically
    }

    packaging {
        resources {
            excludes += "META-INF/versions/9/OSGI-INF/MANIFEST.MF"
        }
    }
    signingConfigs {
        create("release") {
            storeFile = file("release-key")
            storePassword = "Cal2025@BIT!"
            keyAlias = "AICalendarKey"
            keyPassword = "Cal2025@BIT!"
        }
    }
    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_11)
        optIn.add("kotlin.RequiresOptIn")
        allWarningsAsErrors.set(false)
        progressiveMode.set(true)
        verbose.set(true)
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    implementation(libs.compose.navigation)
    implementation(libs.serialization)
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    ksp(libs.androidx.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.prefrencedatastore)
    implementation(libs.protodatastore)
    implementation(libs.roomDatabase)
    implementation(libs.androidx.core.splashscreen )
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
    testImplementation(kotlin("test"))
    // Gson
    implementation(libs.gson)
    // retrofit
    implementation(libs.squareup.retrofit2.retrofit)
    // retrofit gson converter
    implementation(libs.squareup.retrofit2.converter)
    // Request/Response logging
    implementation(libs.okhttp3.logging)
    // okhttp3
    implementation(libs.okhttp3)

    implementation(libs.material.theme.x)
    implementation(platform(libs.firebase.bom))
    implementation(libs.google.services)
//    implementation(libs.firebase.analytics.ktx)
}