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
    implementation(libs.javax.persistence.api)
    // ksp for annotation processing
    kapt(libs.querydsl.apt.get().toString() + ":jpa")

    implementation(libs.querydsl.core)

    // date time: https://github.com/Kotlin/kotlinx-datetime
    implementation(libs.datetime)

    implementation(libs.kotlin.reflect)
    implementation(libs.kotlin.stdlibJdk8)

    implementation(libs.serialization.json)
    implementation(libs.coroutines.core)

    testImplementation(libs.test.mockk)
    testImplementation(libs.test.assertj)
    testImplementation(libs.test.junit.params)
    testRuntimeOnly(libs.test.junit.engine)
}

tasks.withType<Test> {
    useJUnitPlatform()
}
