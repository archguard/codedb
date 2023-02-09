package org.archguard.core

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
    /**
     * project source root / content
     */
    val contentRoot: String get() = ""

    /**
     * project dependencies
     */
    val dependencies: List<Dependency> get() = emptyList()

    /**
     * multiple languages in one project
     */
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
    val scope: String,
    //
    val dependencies: List<Dependency> = emptyList()
)


class LanguageInfo(
    val name: String,
    val displayName: String,
    val version: String
)
