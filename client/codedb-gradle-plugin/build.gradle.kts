repositories {
    gradlePluginPortal()
    mavenCentral()
}

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.jvm)
    alias(libs.plugins.serialization)
    `java-gradle-plugin`
}

dependencies {
    implementation(gradleApi())
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
