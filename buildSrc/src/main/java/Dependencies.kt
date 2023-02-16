import Versions

object Dependencies {

    val retrofit = listOf(
        "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}",
        "com.squareup.retrofit2:converter-gson:${Versions.gsonConverterVersion}",
        "com.squareup.okhttp3:okhttp:${Versions.loggingInterceptorVersion}",
        "com.squareup.okhttp3:logging-interceptor:${Versions.loggingInterceptorVersion}"
    )

    val room = listOf(
        "androidx.room:room-runtime:${Versions.room_version}",
        "androidx.room:room-ktx:${Versions.room_version}"
    )

    const val roomCompilerKapt = "androidx.room:room-compiler:${Versions.room_version}"

    val lifeCycle = listOf(
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifeCycleVersion}",
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifeCycleVersion}",
        "androidx.lifecycle:lifecycle-extensions:${Versions.lifeCycleExtensionsVersion}",
        "androidx.lifecycle:lifecycle-runtime:${Versions.lifeCycleVersion}",
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifeCycleVersion}"
    )

    val daggerHilt = listOf(
        "com.google.dagger:hilt-android:${Versions.hiltAndroidVersion}",
    )

    val daggerKaptHilt = listOf(
        "com.google.dagger:hilt-compiler:${Versions.hiltAndroidVersion}",
        "androidx.hilt:hilt-compiler:${Versions.hiltVersion}"
    )

    object Test {
        const val junit = "junit:junit:${Versions.junitVersion}"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesTestVersion}"
        const val mockitoKotlin = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesTestVersion}"
        const val ioMockitoKotlin = "io.mockk:mockk:${Versions.coroutinesTestVersion}"
        const val mockitoInline = "org.mockito:mockito-inline:${Versions.mockitoInlineVersion}"
        const val assertJ = "org.assertj:assertj-core:${Versions.assertJVersion}"
    }
}
