@file:Suppress("UnstableApiUsage")

rootProject.name = "CodeDB"

enableFeaturePreview("VERSION_CATALOGS")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}

// root project
subproject("factor", "")

include(":structure-ql")
include(":fitness-engine")

taskComponent("core")
basicComponent("gitignore")

fun taskComponent(name: String) = subproject(name, "components/task/")
fun basicComponent(name: String) = subproject(name, "components/basic/")

fun subproject(name: String, parentPath: String) {
    include(name)
    project(":$name").projectDir = file("$parentPath$name")
}
