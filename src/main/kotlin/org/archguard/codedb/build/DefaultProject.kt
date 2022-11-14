package org.archguard.codedb.build

import org.archguard.codedb.build.internal.Project

class DefaultProject : Project {
    override var name: String = ""

    override var displayName: String = ""

    override var description: String = ""

    companion object {
        fun create(): Project {
            return DefaultProject()
        }
    }
}