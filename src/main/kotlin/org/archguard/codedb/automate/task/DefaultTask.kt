package org.archguard.codedb.automate.task

import org.archguard.codedb.core.DefaultProject
import org.archguard.codedb.core.Project
import org.archguard.codedb.automate.task.api.AbstractTask
import org.archguard.codedb.automate.task.api.TaskInputs
import org.archguard.codedb.automate.task.api.TaskOutputs

open class DefaultTask : AbstractTask() {
    override var project: Project = DefaultProject.create()
    override var name: String
        get() = TODO("Not yet implemented")
        set(value) {}
    override var description: String
        get() = TODO("Not yet implemented")
        set(value) {}
    override var group: String
        get() = TODO("Not yet implemented")
        set(value) {}
    override var dependsOn: Set<String>
        get() = TODO("Not yet implemented")
        set(value) {}

    override fun getInputs(): TaskInputs {
        TODO("Not yet implemented")
    }

    override fun getOutputs(): TaskOutputs {
        TODO("Not yet implemented")
    }
}