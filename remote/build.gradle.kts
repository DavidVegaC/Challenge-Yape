plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    kotlin(Config.Plugins.kotlinKapt)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    // Modules
    implementation(project(":data"))
    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.10")
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    // JavaX
    implementation("javax.inject:javax.inject:1")
    // Network (Retrofit, OkHttp, Interceptor, Moshi)
    Dependencies.retrofit.forEach { implementation(it) }

    implementation("com.fasterxml.jackson.core:jackson-annotations:2.14.1")

}