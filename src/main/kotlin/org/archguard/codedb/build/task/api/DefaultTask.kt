package org.archguard.codedb.build.task.api

open class DefaultTask : Task {
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