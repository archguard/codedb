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

    implementation(libs.serialization.json)

    implementation(libs.dataframe.arrow)
    implementation(libs.arrow.vector)
    implementation(libs.chapi.domain)

    testImplementation(libs.bundles.test)
    testRuntimeOnly(libs.test.junit.engine)
}

application {
    mainClass.set("org.archguard.codedb.metric.oo.MainKt")
}

tasks {
    shadowJar {

        manifest {
            attributes(Pair("Main-Class", "org.archguard.codedb.metric.oo.MainKt"))
        }
        // minimize()
        dependencies {
            exclude(dependency("org.junit.jupiter:.*:.*"))
            exclude(dependency("org.junit:.*:.*"))
            exclude(dependency("junit:.*:.*"))
        }
    }
}
