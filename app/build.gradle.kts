plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    id("org.jetbrains.kotlin.kapt")
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

dependencies {

    //version de Dagger Hilt
    val versionDaggerHilt = "2.56"

    // Dagger Hilt
    implementation("com.google.dagger:hilt-android:$versionDaggerHilt")
    kapt("com.google.dagger:hilt-compiler:$versionDaggerHilt")

    //version de la libreria de navegacion de componentes
    val versionDeNavegacion = "2.9.0"

    //Navegacion de componentes
    implementation("androidx.navigation:navigation-fragment-ktx:$versionDeNavegacion")
    implementation("androidx.navigation:navigation-ui-ktx:$versionDeNavegacion")

    // Version de libreria Picaso
    val versionDePicasso = "2.8"

    // Libreria de Picaso
    implementation("com.squareup.picasso:picasso:$versionDePicasso")

    // version de Serialization
    val versionDeSerialization = "1.6.3"

    // Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$versionDeSerialization")


    // version de librerias de Supabase
    val versionDeLibreriaSupabase = "3.2.2"

    // Librerias para Supabase
    implementation(platform("io.github.jan-tennert.supabase:bom:$versionDeLibreriaSupabase"))
    implementation("io.github.jan-tennert.supabase:postgrest-kt")
    implementation("io.ktor:ktor-client-android:$versionDeLibreriaSupabase")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
   }
}