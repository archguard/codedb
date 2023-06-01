@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    base
    alias(libs.plugins.jvm)
    id("jacoco-report-aggregation")

    id("java-library")
    id("maven-publish")
    publishing
    signing
}

jacoco {
    toolVersion = "0.8.8"
}

repositories {
    mavenCentral()
    mavenLocal()
    google()
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "jacoco")

    repositories {
        mavenCentral()
        mavenLocal()
    }

    group = "org.archguard.codedb"
    version = "0.1.0"

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

configure(
    allprojects
            - project(":server")
            - project(":client")
            - project(":submodules")
            - project(":pipeline")
            - project(":workflow-lib")
) {
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")
    apply(plugin = "signing")
    apply(plugin = "publishing")

    publishing {
        publications {
            create<MavenPublication>("mavenJava") {
                from(components["java"])
                versionMapping {
                    usage("java-api") {
                        fromResolutionOf("runtimeClasspath")
                    }
                    usage("java-runtime") {
                        fromResolutionResult()
                    }
                }
                pom {
                    name.set("ArchGuard")
                    description.set(" ArchGuard is a architecture governance tool which can analysis architecture in container, component, code level, create architecture fitness functions, and anaysis system dependencies.. ")
                    url.set("https://archguard.org/")
                    licenses {
                        license {
                            name.set("MIT")
                            url.set("https://raw.githubusercontent.com/archguard/archguard/master/LICENSE")
                        }
                    }
                    developers {
                        developer {
                            id.set("Modernizing")
                            name.set("Modernizing Team")
                            email.set("h@phodal.com")
                        }
                    }
                    scm {
                        connection.set("scm:git:git://github.com/archguard/scanner.git")
                        developerConnection.set("scm:git:ssh://github.com/archguard/scanner.git")
                        url.set("https://github.com/archguard/scanner/")
                    }
                }
            }
        }

        repositories {
            maven {
                val releasesRepoUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                val snapshotsRepoUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
                url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl

                credentials {
                    username =
                        (
                                if (project.findProperty("sonatypeUsername") != null) project.findProperty("sonatypeUsername") else System.getenv(
                                    "MAVEN_USERNAME"
                                )
                                ).toString()
                    password =
                        (
                                if (project.findProperty("sonatypePassword") != null) project.findProperty("sonatypePassword") else System.getenv(
                                    "MAVEN_PASSWORD"
                                )
                                ).toString()
                }
            }
        }
    }

    signing {
        sign(publishing.publications["mavenJava"])
    }

    java {
        withJavadocJar()
        withSourcesJar()
    }
}



// !!!important for jacoco aggregation report only
dependencies {
//	jacocoAggregation(projects.factor)
//	jacocoAggregation(projects.taskCore)
//	jacocoAggregation(projects.feeder.basic.walkdir)
//	jacocoAggregation(projects.feeder.basic.gitignore)
//	jacocoAggregation(projects.feeder.analyser.coverage)
//	jacocoAggregation(projects.structureQl)
//	jacocoAggregation(projects.fitnessEngine)
}
