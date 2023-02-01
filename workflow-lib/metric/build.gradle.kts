@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.jvm)
    alias(libs.plugins.serialization)
}

dependencies {
    implementation(libs.serialization.json)
    implementation(libs.coroutines.core)

    testImplementation(libs.bundles.test)
}
