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
    implementation(libs.serialization.json)
    implementation(libs.coroutines.core)

    implementation(libs.bundles.querydsl)
    implementation(libs.querydsl.mongodb) {
        exclude(group = "org.mongodb", module = "mongo-java-driver")
    }

    implementation(libs.mongodb.morphia)
    implementation(libs.mongodb.driver.reactivestreams)

    testImplementation(libs.bundles.test)
    testRuntimeOnly(libs.test.junit.engine)
}

tasks.withType<Test> {
    useJUnitPlatform()
}

