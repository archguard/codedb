@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.jvm)
    alias(libs.plugins.serialization)
}

dependencies {
    implementation(libs.serialization.json)

    // Logging
    implementation(libs.logging.slf4j.api)
    implementation(libs.logging.logback.classic)

    // DSL
    implementation(libs.jupyter.api)
    implementation(libs.jupyter.kernel)
    compileOnly(libs.kotlin.scriptingJvm)

    implementation(libs.archguard.dsl) {
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-test-junit")
    }

    testImplementation(libs.bundles.test)
}
