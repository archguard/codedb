package org.archguard.codedb.automate.task

import org.archguard.codedb.automate.DefaultProject
import org.archguard.codedb.automate.internal.Project
import org.archguard.codedb.automate.task.api.AbstractTask

open class DefaultTask : AbstractTask() {
    override var project: Project = DefaultProject.create()
}