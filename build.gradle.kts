import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
	alias(libs.plugins.jvm)
	alias(libs.plugins.serialization)
	alias(libs.plugins.springboot)
	alias(libs.plugins.jupyter)

	id("jacoco-report-aggregation")
}

jacoco {
	toolVersion = "0.8.8"
}

allprojects {
	apply(plugin = "java")
	apply(plugin = "jacoco")

	repositories {
		mavenCentral()
		mavenLocal()
	}

	group = "org.archguard.codedb"
	version = "0.1.0-SNAPSHOT"

	java.sourceCompatibility = JavaVersion.VERSION_11
	java.targetCompatibility = JavaVersion.VERSION_11

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
	api(projects.factor)
	api(projects.components.core)

	implementation(libs.bundles.springboot)

	developmentOnly(libs.springboot.devtools)

	implementation(libs.serialization.json)
	implementation(libs.jackson.kotlin)

	implementation(libs.kotlin.reflect)

	implementation(libs.coroutines.core)
	implementation(libs.coroutines.reactor)
	implementation(libs.reactor.kotlin.ext)

	// for chapi
	implementation(libs.chapi.domain) {
		exclude(group = "org.jetbrains.kotlin", module = "kotlin-test-junit")
	}
	implementation(libs.chapi.java) {
		exclude(group = "org.jetbrains.kotlin", module = "kotlin-test-junit")
	}

	// DSL
	implementation(libs.jupyter.api)
	implementation(libs.jupyter.kernel)
	compileOnly(libs.kotlin.scriptingJvm)
	implementation(libs.archguard.dsl) {
		exclude(group = "org.jetbrains.kotlin", module = "kotlin-test-junit")
	}

	// for tasking
	testImplementation(libs.bundles.test)

	testImplementation(libs.springboot.test)
	testImplementation(libs.test.reactor)
	testImplementation(libs.test.embed.mongodb)
}

// !!!important for jacoco aggregation report only
dependencies {
	jacocoAggregation(projects.factor)
	jacocoAggregation(projects.components.task.core)
	jacocoAggregation(projects.components.basic.walkdir)
	jacocoAggregation(projects.components.basic.gitignore)
	jacocoAggregation(projects.components.feeder.coverage)
	jacocoAggregation(projects.structureQl)
	jacocoAggregation(projects.fitnessEngine)
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}
