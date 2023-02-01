@file:Suppress("UnstableApiUsage")

rootProject.name = "CodeDB"

enableFeaturePreview("VERSION_CATALOGS")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

// for normal projects
include(":factor")
include(":structure-ql")
include(":fitness-engine")


include(":components:core")

include(":components:task:core")
include(":components:task:automate")

include(":components:basic:gitignore")
include(":components:basic:walkdir")

include(":components:feeder:coverage")
