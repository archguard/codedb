@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.jvm)
    alias(libs.plugins.serialization)
}

dependencies {
    implementation(projects.language.llmCore)

    implementation(libs.kotlin.stdlib)

    testImplementation(libs.bundles.test)
    testRuntimeOnly(libs.test.junit.engine)
}
