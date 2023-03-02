@file:Suppress("UnstableApiUsage")

rootProject.name = "CodeDB"

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":api-examples:getting-started")

include(":server")

// TODO: spike for core usage
 include(":core")

include(":submodules:gitignore")
include(":submodules:walkdir")

include(":client:client-api")
include(":client:codedb-cli")
include(":client:codedb-clui")
include(":client:codedb-gradle-plugin")

// for server/cli/other usage
include(":workflow-lib:factor")
include(":workflow-lib:fitness-engine")
include(":workflow-lib:repl")

include(":pipeline:runner")
include(":pipeline:action-toolkit")

include(":pipeline:checkout")
include(":pipeline:coverage")
include(":pipeline:oo-metric")
