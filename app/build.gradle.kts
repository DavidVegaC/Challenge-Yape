plugins {
    id(Config.Plugins.android)
    kotlin(Config.Plugins.kotlinAndroid)
    kotlin(Config.Plugins.kotlinKapt)
    id(Config.Plugins.dagger)
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.davega.recetasyape"
    compileSdk = Config.Android.androidCompileSdkVersion

    defaultConfig {
        applicationId = Environments.Release.appId
        minSdk = Config.Android.androidMinSdkVersion
        targetSdk = Config.Android.androidTargetSdkVersion
        versionCode = Environments.Release.appVersionCode
        versionName = Environments.Release.appVersionName

        testInstrumentationRunner = Config.testRunner

        // Configs
        buildConfigField("String", "BASE_URL", "\"" + Environments.Release.baseUrl + "\"")
    }

    buildTypes {
        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.4"
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":cache"))
    implementation(project(":remote"))

    // JavaX
    implementation("javax.inject:javax.inject:1")

    //Lottie
    implementation("com.airbnb.android:lottie:3.7.0")
    //Timber
    implementation("com.jakewharton.timber:timber:5.0.1")

    //Dagger-Hilt
    Dependencies.daggerHilt.forEach {
        implementation(it)
    }
    Dependencies.daggerKaptHilt.forEach {
        kapt(it)
    }

    //google-maps
    implementation("com.google.android.libraries.maps:maps:3.1.0-beta")
    implementation("com.google.maps.android:maps-v3-ktx:3.4.0")
    constraints {
        // Volley is a transitive dependency of maps
        implementation("com.android.volley:volley:1.2.1") {
            because("Only volley 1.2.0 or newer are available on maven.google.com")
        }
    }

    //COMPOSE
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.runtime:runtime")
    implementation("androidx.compose.material:material")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.foundation:foundation-layout")
    implementation("androidx.compose.animation:animation")
    implementation("androidx.compose.ui:ui-tooling")
    implementation("com.google.accompanist:accompanist-themeadapter-material:0.28.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.1")
    implementation("androidx.compose.runtime:runtime-livedata:1.4.2")
    implementation("androidx.navigation:navigation-compose:2.5.3")
    implementation("io.coil-kt:coil-compose:2.3.0")

    // compose navigation
    implementation("androidx.navigation:navigation-compose:2.5.3")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    //test
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    testImplementation("androidx.arch.core:core-testing:2.1.0")
    testImplementation(Dependencies.Test.ioMockitoKotlin)
    testImplementation(Dependencies.Test.assertJ)

    implementation("com.google.android.material:material:1.8.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}