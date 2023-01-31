@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.jvm)
    alias(libs.plugins.serialization)
}

dependencies {
    api(projects.components.core)

    implementation(libs.kotlin.reflect)
    implementation(libs.kotlin.stdlib)

    // java
    implementation(libs.jacoco.core)

    implementation(libs.bundles.test)
}