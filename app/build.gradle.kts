plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.coco.primeraapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.primeraapp"
        minSdk = 27
        targetSdk = 34
        versionCode = 5
        versionName = "1.5"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    // ViewBinding enabled for this module
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // Dependencias de Kotlin
    implementation(libs.material.spinner)
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.activity)

    // Consumo de servicios
    implementation(libs.retrofit) // Retrofit
    implementation(libs.logging.interceptor) // Interceptor
    implementation(libs.converter.gson) // Gson

    // Dependencias de Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Imagenes
    implementation(libs.glide)
    annotationProcessor(libs.compiler)

    // Almacenamiento de datos
    implementation(libs.datastore.preferences)
}
