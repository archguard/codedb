@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.jvm)
    alias(libs.plugins.serialization)
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlin.stdlib)

    // java
    implementation(libs.jacoco.core)
    implementation(libs.regex.re2)

    implementation(libs.bundles.test)
}