@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.jvm)
    alias(libs.plugins.serialization)
}

dependencies {
    implementation(projects.core)

    implementation(libs.serialization.json)
    implementation(libs.gson)

    testImplementation(libs.bundles.test)
}
