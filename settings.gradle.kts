@file:Suppress("UnstableApiUsage")

rootProject.name = "CodeDB"

pluginManagement {
    repositories {
        gradlePluginPortal()
        // for CLUI
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

enableFeaturePreview("VERSION_CATALOGS")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":api-examples:getting-started")

include(":feeder:core")
include(":server")

include(":core-model")
include(":core")

include(":feeder:basic:gitignore")
include(":feeder:basic:walkdir")

include(":feeder:analyser:coverage")

include(":client:client-api")
include(":client:codedb-cli")
include(":client:codedb-clui")
include(":client:codedb-gradle-plugin")

// for server/cli/other usage
include(":workflow-lib:factor")
include(":workflow-lib:metric")
include(":workflow-lib:structure-ql")
include(":workflow-lib:fitness-engine")
include(":workflow-lib:task-core")
include(":workflow-lib:repl")

include(":action:checkout")
include(":action:runner")

//fun libSubproject(name: String) = workflow(name, "workflow-lib/")
//fun workflow(name: String, parentPath: String) {
//    include(name)
//    project(":$name").projectDir = file("$parentPath$name")
//}
