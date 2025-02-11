plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}


android {
    namespace = "com.example.dentex"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.dentex"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
//    implementation("com.google.firebase:firebase-messaging:23.1.0")
    implementation(libs.firebase.ui.firestore)
    implementation (libs.material.v121)
    implementation ("androidx.work:work-runtime:2.7.1") // WorkManager dependency
    implementation ("androidx.core:core:1.6.0") // For NotificationManager
    implementation ("androidx.appcompat:appcompat:1.3.0") // For AppCompat (if needed)
    implementation ("com.google.guava:guava:30.1-jre") // Add this line
    implementation ("androidx.work:work-runtime:2.7.1")
}