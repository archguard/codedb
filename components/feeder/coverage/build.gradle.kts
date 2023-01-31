@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.jvm)
    alias(libs.plugins.serialization)
}

dependencies {
    implementation(projects.components.core)

    implementation(libs.kotlin.reflect)
    implementation(libs.kotlin.stdlib)

    // Logging
    implementation(libs.logging.slf4j.api)
    implementation(libs.logging.logback.classic)

    implementation(libs.openclover.core)
    // java
    implementation(libs.jacoco.core)

    testImplementation(libs.bundles.test)
    testRuntimeOnly(libs.test.junit.engine)
}
