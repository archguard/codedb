@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    idea
    alias(libs.plugins.jvm)
    alias(libs.plugins.serialization)

    kotlin("kapt")
}


idea {
    module {
        val kaptMain = file("build/generated/source/kapt/main")
        sourceDirs.plusAssign(kaptMain)
        generatedSourceDirs.plusAssign(kaptMain)
    }
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    testImplementation(libs.test.mockk)
    testImplementation(libs.test.assertj)

    testImplementation(libs.test.junit.params)
    testRuntimeOnly(libs.test.junit.engine)
}

tasks.withType<Test> {
    useJUnitPlatform()
}
