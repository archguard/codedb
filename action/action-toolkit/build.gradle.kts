@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.jvm)
}

dependencies {
    // Logging
    api(libs.logging.slf4j.api)

    testImplementation(libs.bundles.test)
}
