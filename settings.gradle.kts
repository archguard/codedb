pluginManagement {
	repositories {
		maven { url = uri("https://repo.spring.io/milestone") }
		maven { url = uri("https://repo.spring.io/snapshot") }
		gradlePluginPortal()
	}
}
rootProject.name = "CodeDB"


include(":structure-ql")
include(":factor")
include(":fitness-engine")

include(":components:task:core")

include(":components:basic:gitignore")
