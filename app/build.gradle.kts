plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    id("org.jetbrains.kotlin.kapt")
    id("androidx.navigation.safeargs.kotlin")
    kotlin("plugin.serialization") version "2.2.0"
}

android {
    namespace = "com.benjamin.proyectofeedo"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.benjamin.proyectofeedo"
        minSdk = 24
        targetSdk = 35
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

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    // Hilt
    val versionDaggerHilt = "2.56"
    implementation("com.google.dagger:hilt-android:$versionDaggerHilt")
    kapt("com.google.dagger:hilt-compiler:$versionDaggerHilt")

    // Navigation
    val versionDeNavegacion = "2.9.0"
    implementation("androidx.navigation:navigation-fragment-ktx:$versionDeNavegacion")
    implementation("androidx.navigation:navigation-ui-ktx:$versionDeNavegacion")

    // Picasso
    implementation("com.squareup.picasso:picasso:2.8")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Material
    implementation("com.google.android.material:material:1.12.0")

    // Supabase
    val supabase_version = "3.2.2"
    val ktor_version = "3.2.3"
    implementation(platform("io.github.jan-tennert.supabase:bom:$supabase_version"))
    implementation("io.github.jan-tennert.supabase:postgrest-kt")
    implementation("io.github.jan-tennert.supabase:storage-kt")
    implementation("io.ktor:ktor-client-android:$ktor_version")

    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.1.7")

    // Image picker
    implementation(libs.imagePicker)

    // Room
    implementation(libs.androidx.room.runtime.android)

    //  Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.4.0"))
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-storage-ktx")

    // Core AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Tests
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
