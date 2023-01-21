plugins {
    `java-library`
    alias(libs.plugins.jvm)
    kotlin("plugin.serialization") version "1.6.21"
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    api(project(":factor"))

    implementation("com.phodal.chapi:chapi-ast-kotlin:2.0.0-beta.9") {
        // around 10mb, only documents files, exclude (reuse in cli?)
        exclude(group = "com.ibm.icu", module = "icu4j")
    }

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("com.phodal.chapi:chapi-domain:2.0.0-beta.9")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")

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

    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

