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
