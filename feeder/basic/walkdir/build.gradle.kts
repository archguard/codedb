@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.jvm)
    alias(libs.plugins.serialization)
}

dependencies {
    implementation(projects.feeder.core)

    implementation(libs.coroutines.core)
    implementation(libs.jgit)

    // Logging
    implementation(libs.logging.slf4j.api)
    implementation(libs.logging.logback.classic)

    testImplementation(libs.bundles.test)
}
