@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.jvm)
    alias(libs.plugins.serialization)
    alias(libs.plugins.compose)
}

dependencies {
    // Logging
    implementation(libs.logging.slf4j.api)
    implementation(libs.logging.logback.classic)

    testImplementation(libs.bundles.test)
    implementation(compose.desktop.currentOs)
}

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

compose.desktop {
    application {
        mainClass = "org.archguard.codedb.clui.CodeDBKt"
    }
}
