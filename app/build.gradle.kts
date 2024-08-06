plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.foodiefinder"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.smolsashimi.foodiefinder"
        minSdk = 24
        targetSdk = 34
        versionCode = 3
        versionName = "1.2"

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
}

dependencies {
    implementation (libs.appcompat.v170)
    implementation (libs.material.v190)
    implementation (libs.activity.ktx)
    implementation (libs.constraintlayout)
    testImplementation (libs.junit)
    testImplementation(libs.junit.jupiter)
    androidTestImplementation (libs.junit.v121)
    androidTestImplementation (libs.espresso.core.v361)

    // Room components
    implementation (libs.room.runtime)
    annotationProcessor (libs.room.compiler)

    // Mockito
    testImplementation (libs.mockito.core)
    testImplementation (libs.mockito.inline)
}
