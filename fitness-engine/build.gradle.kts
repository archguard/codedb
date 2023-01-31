@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    idea
    alias(libs.plugins.jvm)
    alias(libs.plugins.serialization)
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    testImplementation(libs.bundles.test)
    testRuntimeOnly(libs.test.junit.engine)
}
