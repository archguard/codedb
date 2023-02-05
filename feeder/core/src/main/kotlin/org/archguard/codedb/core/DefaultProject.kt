package org.archguard.codedb.core

/**
 * align IDEA project model with Gradle project model
 * keep same to: https://www.phodal.com/blog/gradle-idea-project-model/
 **/
class DefaultProject(
    override var name: String = "",
    override var displayName: String = "",
    override var description: String = "",
    override var workdir: String = "",
    override var basedir: String = "",
    override var modules: List<Project> = emptyList()
) : Project {
    // TODO: add more properties
    val contentRoot: String get() = ""

    val dependency: List<Dependency> get() = emptyList()

    val languages: List<LanguageInfo> get() = emptyList()

    companion object {
        fun create(): Project {
            return DefaultProject()
        }
    }
}

// to tree
class Dependency(
    val name: String,
    val version: String,
    val scope: String
)


class LanguageInfo(
    val name: String,
    val displayName: String,
    val version: String
)
