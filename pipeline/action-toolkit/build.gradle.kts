@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.jvm)
    alias(libs.plugins.serialization)
}

dependencies {
    // Logging
    api(libs.logging.slf4j.api)

    // http
    api(libs.ktor.client.core)
    api(libs.ktor.client.cio)
    api(libs.ktor.serialization.json)
    api(libs.ktor.client.content.negotiation)

    testImplementation(libs.bundles.test)
}
