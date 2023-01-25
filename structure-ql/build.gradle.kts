@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    `java-library`
    alias(libs.plugins.jvm)
    alias(libs.plugins.serialization)
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    api(project(":factor"))

    implementation(libs.chapi.kotlin) {
        // around 10mb, only documents files, exclude (reuse in cli?)
        exclude(group = "com.ibm.icu", module = "icu4j")
    }
    implementation(libs.chapi.domain)

    implementation(libs.kotlin.reflect)
    implementation(libs.kotlin.stdlibJdk8)

    implementation(libs.serialization.json)
    implementation(libs.coroutines.core)

    implementation(libs.querydsl.core)
    implementation(libs.querydsl.apt)
    implementation(libs.querydsl.kotlin)
    implementation(libs.querydsl.mongodb) {
        exclude(group = "org.mongodb", module = "mongo-java-driver")
    }

    implementation(libs.mongodb.morphia)
//    implementation("dev.morphia.morphia:morphia-core:2.2.10")
    implementation(libs.mongodb.driver.reactivestreams)

    testImplementation(libs.test.mockk)
    testImplementation(libs.test.assertj)

    // Test dependencies: kotlin-test and Junit 5
    testImplementation(libs.test.junit.params)
    testRuntimeOnly(libs.test.junit.engine)
    testImplementation(libs.test.kotlintest.assertions)
}

tasks.withType<Test> {
    useJUnitPlatform()
}

