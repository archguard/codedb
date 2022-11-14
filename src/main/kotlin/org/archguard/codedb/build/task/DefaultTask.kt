package org.archguard.codedb.build.task

import org.archguard.codedb.build.DefaultProject
import org.archguard.codedb.build.internal.Project
import org.archguard.codedb.build.task.api.AbstractTask

open class DefaultTask : AbstractTask() {
    override var project: Project = DefaultProject.create()
}