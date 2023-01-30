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
    implementation(libs.coroutines.core)

    // Logging
    implementation(libs.logging.slf4j.api)
    implementation(libs.logging.logback.classic)

    testImplementation(libs.bundles.test)
}
