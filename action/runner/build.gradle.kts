@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.jvm)
    alias(libs.plugins.serialization)
}

dependencies {
    implementation(projects.core)

    implementation(libs.gson)
    implementation(libs.serialization.json)

    implementation(libs.kaml)

//    implementation(libs.jackson.yaml)

    testImplementation(libs.bundles.test)
}
