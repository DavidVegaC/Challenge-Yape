plugins {
    id(Config.Plugins.javaLibrary)
    id(Config.Plugins.kotlin)
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.10")
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    // JavaX
    implementation("javax.inject:javax.inject:1")

    // Test Dependencies
    testImplementation(Dependencies.Test.junit)
    testImplementation(Dependencies.Test.ioMockitoKotlin)
    testImplementation(Dependencies.Test.assertJ)
}