import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	alias(libs.plugins.jvm)
	alias(libs.plugins.serialization)

	id("org.springframework.boot") version "2.7.6-SNAPSHOT"
	id("io.spring.dependency-management") version "1.1.0"

	id("org.jetbrains.kotlin.jupyter.api") version "0.11.0-89-1"

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
	java.sourceCompatibility = JavaVersion.VERSION_11
	java.targetCompatibility = JavaVersion.VERSION_11

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

	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
	implementation("org.springframework.boot:spring-boot-starter-webflux")

	developmentOnly("org.springframework.boot:spring-boot-devtools")

	implementation(libs.serialization.json)
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")

	implementation(libs.kotlin.reflect)
	implementation(libs.kotlin.stdlibJdk8)

	implementation(libs.coroutines.core)
	implementation(libs.coroutines.reactor)

	// DSL
	implementation("org.jetbrains.kotlinx:kotlin-jupyter-api:0.11.0-89-1")
	implementation("org.jetbrains.kotlinx:kotlin-jupyter-kernel:0.11.0-89-1")

	compileOnly(libs.kotlin.scriptingJvm)

	// Logging
	implementation(libs.logging.slf4j.api)
	implementation(libs.logging.logback.classic)

	// for tasking
	testImplementation(libs.test.kotlintest.assertions)
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")

	testImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo")

	// for chapi
	implementation(libs.chapi.domain)
	implementation(libs.chapi.java)
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
