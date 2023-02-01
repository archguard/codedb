@file:Suppress("UnstableApiUsage")

rootProject.name = "CodeDB"

enableFeaturePreview("VERSION_CATALOGS")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":api-examples:getting-started")

include(":components:core")

include(":components:task:core")
include(":components:task:automate")

include(":components:basic:gitignore")
include(":components:basic:walkdir")

include(":components:feeder:coverage")

// for server/cli/other usage
libSubproject("factor")
libSubproject("metric")
libSubproject("structure-ql")
libSubproject("fitness-engine")

fun libSubproject(name: String) = subproject(name, "workflow-lib/")
fun subproject(name: String, parentPath: String) {
    include(name)
    project(":$name").projectDir = file("$parentPath$name")
}
