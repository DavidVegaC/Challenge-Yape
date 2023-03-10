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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":cache"))
    implementation(project(":remote"))

    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.3")
    implementation("androidx.activity:activity-ktx:1.6.1")

    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.10")
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    // JavaX
    implementation("javax.inject:javax.inject:1")

    //Lottie
    implementation("com.airbnb.android:lottie:3.7.0")
    //Timber
    implementation("com.jakewharton.timber:timber:5.0.1")

    //Glide
    implementation("com.github.bumptech.glide:glide:4.14.2")
    kapt("com.github.bumptech.glide:compiler:4.14.2")

    implementation("com.makeramen:roundedimageview:2.3.0")

    //lifecycle
    Dependencies.lifeCycle.forEach {
        implementation(it)
    }

    //Dagger-Hilt
    Dependencies.daggerHilt.forEach {
        implementation(it)
    }
    Dependencies.daggerKaptHilt.forEach {
        kapt(it)
    }

    //google-maps
    implementation("com.google.android.gms:play-services-maps:18.1.0")

    //test
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    testImplementation("androidx.arch.core:core-testing:2.1.0")
    testImplementation(Dependencies.Test.ioMockitoKotlin)
    testImplementation(Dependencies.Test.assertJ)

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}