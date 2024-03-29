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

dependencies {
    implementation(libs.javax.persistence.api)
    // ksp for annotation processing
    kapt(libs.querydsl.apt.get().toString() + ":jpa")

    implementation(libs.querydsl.core)

    implementation(libs.datetime)

    implementation(libs.kotlin.reflect)
    implementation(libs.serialization.json)
    implementation(libs.coroutines.core)

    testImplementation(libs.bundles.test)
}
