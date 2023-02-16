object Config {

    object Android {
        // Android sdk and version
        const val androidMinSdkVersion = 24
        const val androidTargetSdkVersion = 33
        const val androidCompileSdkVersion = 33
        const val androidBuildToolsVersion = "30.0.2"
    }

    object Plugins {
        const val kotlin = "kotlin"
        const val javaLibrary = "java-library"
        const val android = "com.android.application"
        const val kotlinAndroid = "android"
        const val navigationSafArgs = "androidx.navigation.safeargs.kotlin"
        const val kotlinKapt = "kapt"
        const val dagger = "dagger.hilt.android.plugin"
        const val androidLibrary = "com.android.library"
    }

    const val testRunner = "androidx.test.runner.AndroidJUnitRunner"
}