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
    implementation("javax.persistence:javax.persistence-api:2.2")
    // ksp for annotation processing
    kapt("com.querydsl:querydsl-apt:5.0.0:jpa")
    implementation("com.querydsl:querydsl-core:5.0.0")

//    todo: move to split module for ArchGuard
//    implementation("com.phodal.chapi:chapi-domain:2.0.0-beta.9")

    // date time: https://github.com/Kotlin/kotlinx-datetime
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")

    // test
    implementation(kotlin("test"))
    implementation(kotlin("test-junit"))

    testImplementation("io.mockk:mockk:1.12.3")
    testImplementation("org.assertj:assertj-core:3.22.0")

    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
