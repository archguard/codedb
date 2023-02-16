@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.jvm)
    alias(libs.plugins.serialization)
}

dependencies {
    implementation(libs.coroutines.core)

    // Logging
    implementation(libs.logging.kotlin)

    testImplementation(libs.jimfs)
    testImplementation(libs.bundles.test)
}
