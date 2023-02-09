package org.archguard.codedb.automate.task.api

import org.archguard.core.Project

abstract class AbstractTask() : Task {
    open lateinit var project: Project
    override fun setDependsOn(dependsOnTasks: Iterable<*>) {
        TODO("Not yet implemented")
    }

    override fun dependsOn(vararg tables: Any): Task {
        TODO("Not yet implemented")
    }

    override fun compareTo(other: Task): Int {
        TODO("Not yet implemented")
    }
}