@file:Suppress("UnstableApiUsage")

rootProject.name = "CodeDB"

enableFeaturePreview("VERSION_CATALOGS")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

// for normal projects
include(":factor")
include(":structure-ql")
include(":fitness-engine")

taskComponent("core")
basicComponent("gitignore")

fun taskComponent(name: String) = component(name, "components/task/")
fun basicComponent(name: String) = component(name, "components/basic/")

fun subproject(name: String, parentPath: String) {
    include(name)
    project(":$name").projectDir = file("$parentPath$name")
}

fun component(name: String, parentPath: String) {
    include(":components:$name")
    project(":components:$name").projectDir = file("$parentPath$name")
}
