plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    id("org.jetbrains.kotlin.kapt")
    id("androidx.navigation.safeargs.kotlin")
    kotlin("plugin.serialization") version "2.2.0"
    id("kotlin-parcelize")
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

        //Retrofit
        implementation("com.squareup.retrofit2:retrofit:2.9.0")
        implementation("com.squareup.retrofit2:converter-gson:2.9.0")
        implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")


        // Version de libreria material para la pantalla de login
        val versionDeMaterial = "1.12.0"

        //contraseña en login
        implementation("com.google.android.material:material:$versionDeMaterial")


        //versiones de supabase
        val supabase_version = "3.2.2"
        val ktor_version = "3.0.3"

        // Agregar los módulos que necesitás explícitamente
        implementation("io.github.jan-tennert.supabase:postgrest-kt:$supabase_version")
        implementation("io.github.jan-tennert.supabase:storage-kt:$supabase_version")
        implementation("io.github.jan-tennert.supabase:auth-kt:$supabase_version") // 👈 auth-kt en vez de gotrue-kt
        implementation("io.ktor:ktor-client-android:$ktor_version")
        implementation("io.ktor:ktor-client-core:$ktor_version")

        //Verision de dataStore
        val dataStore_Version = "1.1.7"

        // DataStore
        implementation("androidx.datastore:datastore-preferences:$dataStore_Version")

        // Google in nativo abajo

        // Credential Manager (para el flujo nativo de Google)
        implementation("androidx.credentials:credentials:1.3.0")
        implementation("androidx.credentials:credentials-play-services-auth:1.3.0")

        // Google ID Token
        implementation("com.google.android.libraries.identity.googleid:googleid:1.1.1")


        //image picker
        implementation(libs.imagePicker)

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