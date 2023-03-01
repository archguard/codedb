@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    java
    application
    alias(libs.plugins.jvm)
    alias(libs.plugins.serialization)
    alias(libs.plugins.shadow)

    kotlin("kapt")
}

dependencies {
    implementation(projects.pipeline.actionToolkit)
    implementation(projects.core)

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

    implementation(libs.jcommander)

    testImplementation(libs.bundles.test)
    testRuntimeOnly(libs.test.junit.engine)
}

application {
    mainClass.set("org.archguard.codedb.coverage.MainKt")
}

tasks {
    shadowJar {

        manifest {
            attributes(Pair("Main-Class", "org.archguard.codedb.coverage.MainKt"))
        }
        // minimize()
        dependencies {
            exclude(dependency("org.junit.jupiter:.*:.*"))
            exclude(dependency("org.junit:.*:.*"))
            exclude(dependency("junit:.*:.*"))
        }
    }
}
