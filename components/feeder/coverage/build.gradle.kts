@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    java
    alias(libs.plugins.jvm)
    alias(libs.plugins.serialization)

    kotlin("kapt")
}

dependencies {
    implementation(projects.components.core)

    implementation(libs.kotlin.reflect)
    implementation(libs.kotlin.stdlib)

    // Logging
    implementation(libs.logging.slf4j.api)
    implementation(libs.logging.logback.classic)

    // java
    implementation(libs.jacoco.core)
    implementation(libs.guava)
    implementation(libs.gson)

    compileOnly(libs.auto.value.annotations)
    kapt(libs.auto.value)
//    implementation(libs.auto.value)


    implementation(libs.jcommander)

    testImplementation(libs.bundles.test)
    testRuntimeOnly(libs.test.junit.engine)
}
