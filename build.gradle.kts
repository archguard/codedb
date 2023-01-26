import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
	alias(libs.plugins.jvm)
	alias(libs.plugins.serialization)
	alias(libs.plugins.springboot)
	alias(libs.plugins.jupyter)

	id("io.spring.dependency-management") version "1.1.0"
	id("jacoco-report-aggregation")
}

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
	maven { url = uri("https://repo.spring.io/snapshot") }
}


jacoco {
	toolVersion = "0.8.7"
}

allprojects {
	apply(plugin = "java")
	apply(plugin = "jacoco")

	group = "org.archguard.codedb"
	version = "0.1.0-SNAPSHOT"

//	java.sourceCompatibility = JavaVersion.VERSION_11
//	java.targetCompatibility = JavaVersion.VERSION_11

	tasks.getByName<Test>("test") {
		useJUnitPlatform()
	}

	tasks.withType<Test> {
		useJUnitPlatform()
		testLogging {
			events("passed", "skipped", "failed")
		}
	}

	tasks.test {
		finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
	}

	tasks.jacocoTestReport {
		dependsOn(tasks.test) // tests are required to run before generating the report
	}

	tasks.jacocoTestReport {
		reports {
			xml.required.set(true)
			csv.required.set(false)
			html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
		}
	}

}

dependencies {
	api(project(":factor"))

    implementation(libs.bundles.springboot)

    developmentOnly(libs.springboot.devtools)

    implementation(libs.serialization.json)
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")

	implementation(libs.kotlin.reflect)
	implementation(libs.kotlin.stdlibJdk8)

	implementation(libs.coroutines.core)
	implementation(libs.coroutines.reactor)

	// DSL
	implementation(libs.jupyter.api)
	implementation(libs.jupyter.kernel)

	compileOnly(libs.kotlin.scriptingJvm)

	// Logging
	implementation(libs.logging.slf4j.api)

	// for tasking
	testImplementation(libs.bundles.test)

	testImplementation(libs.springboot.test)
	testImplementation(libs.test.reactor)
	testImplementation(libs.test.embed.mongodb)

	// for chapi
	implementation(libs.chapi.domain)
	implementation(libs.chapi.java)
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
