package org.archguard.codedb.core

class DefaultProject(
    override var name: String = "",
    override var displayName: String = "",
    override var description: String = "",
    override var workdir: String = "",
    override var basedir: String = "",
    override var subProjects: List<Project> = emptyList()
) : Project {

    companion object {
        fun create(): Project {
            return DefaultProject()
        }
    }
}
