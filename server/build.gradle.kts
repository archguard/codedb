import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
	alias(libs.plugins.jvm)
	alias(libs.plugins.serialization)
	alias(libs.plugins.springboot)
	alias(libs.plugins.jupyter)
}

dependencies {
	api(projects.workflowLib.factor)
	api(projects.workflowLib.metric)
	api(projects.workflowLib.repl)
	implementation(projects.core)

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

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}
