@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    application
    alias(libs.plugins.jvm)
    alias(libs.plugins.serialization)
    alias(libs.plugins.shadow)
}

dependencies {
    implementation(libs.clikt)

    implementation(projects.core)

    implementation(libs.serialization.json)

    // HttpRequest
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)

    // Logging
    implementation(libs.logging.slf4j.api)
    implementation(libs.logging.logback.classic)


    implementation(libs.kaml)

    testImplementation(libs.bundles.test)
}


application {
    mainClass.set("org.archguard.runner.RunnerKt")
}

tasks {
    shadowJar {

        manifest {
            attributes(Pair("Main-Class", "org.archguard.runner.RunnerKt"))
        }
        // minimize()
        dependencies {
            exclude(dependency("org.junit.jupiter:.*:.*"))
            exclude(dependency("org.junit:.*:.*"))
            exclude(dependency("junit:.*:.*"))
        }
    }
}
