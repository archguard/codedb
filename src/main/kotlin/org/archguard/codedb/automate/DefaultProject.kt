package org.archguard.codedb.automate

import org.archguard.codedb.automate.internal.Project

class DefaultProject(
    override var name: String = "",
    override var displayName: String = "",
    override var description: String = ""
) : Project {

    companion object {
        fun create(): Project {
            return DefaultProject()
        }
    }
}