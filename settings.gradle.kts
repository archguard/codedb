@file:Suppress("UnstableApiUsage")

rootProject.name = "CodeDB"

enableFeaturePreview("VERSION_CATALOGS")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

// for normal projects
include(":factor")
include(":structure-ql")
include(":fitness-engine")


include(":components:task:core")
include(":components:task:coverage")

include(":components:basic:gitignore")

include(":components:feeder:coverage")
