@file:Suppress("UnstableApiUsage")

rootProject.name = "CodeDB"

enableFeaturePreview("VERSION_CATALOGS")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":api-examples:getting-started")

include(":feeder:core")

include(":feeder:basic:gitignore")
include(":feeder:basic:walkdir")

include(":feeder:analyser:coverage")

include(":client:codedb-cli")
include(":client:codedb-gradle-plugin")

// for server/cli/other usage
libSubproject("factor")
libSubproject("metric")
libSubproject("structure-ql")
libSubproject("math-engine")
libSubproject("task-core")

fun libSubproject(name: String) = workflow(name, "workflow-lib/")
fun workflow(name: String, parentPath: String) {
    include(name)
    project(":$name").projectDir = file("$parentPath$name")
}
