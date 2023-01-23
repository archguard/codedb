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

    // mongodb query dsl
    implementation("org.mongodb.morphia:morphia:1.3.2")
//    implementation("dev.morphia.morphia:morphia-core:2.2.10")

    implementation("com.querydsl:querydsl-core:5.0.0")
    implementation("com.querydsl:querydsl-apt:5.0.0")
    implementation("com.querydsl:querydsl-kotlin:5.0.0")
    implementation("com.querydsl:querydsl-mongodb:5.0.0") {
        exclude(group = "org.mongodb", module = "mongo-java-driver")
    }

    implementation("org.mongodb:mongodb-driver-reactivestreams:4.8.0")

    // test
    implementation(kotlin("test"))
    implementation(kotlin("test-junit"))

    testImplementation("io.mockk:mockk:1.12.3")
    testImplementation("org.assertj:assertj-core:3.22.0")

    // Test dependencies: kotlin-test and Junit 5
    testImplementation(libs.test.junit.params)
    testImplementation(libs.test.kotlintest.assertions)
    testRuntimeOnly(libs.test.junit.engine)
}

tasks.withType<Test> {
    useJUnitPlatform()
}

