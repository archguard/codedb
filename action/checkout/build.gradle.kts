@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    application
    alias(libs.plugins.jvm)
    alias(libs.plugins.shadow)
}

dependencies {
    testImplementation(libs.bundles.test)
}

application {
    mainClass.set("org.archguard.action.checkout.MainKt")
}

tasks {
    shadowJar {

        manifest {
            attributes(Pair("Main-Class", "org.archguard.action.checkout.MainKt"))
        }
        // minimize()
        dependencies {
            exclude(dependency("org.junit.jupiter:.*:.*"))
            exclude(dependency("org.junit:.*:.*"))
            exclude(dependency("junit:.*:.*"))
        }
    }
}
