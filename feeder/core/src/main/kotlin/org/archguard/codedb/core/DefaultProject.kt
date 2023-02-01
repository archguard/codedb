package org.archguard.codedb.core

class DefaultProject(
    override var name: String = "",
    override var displayName: String = "",
    override var description: String = "",
    override var workdir: String = ""
) : Project {

    companion object {
        fun create(): Project {
            return DefaultProject()
        }
    }
}
