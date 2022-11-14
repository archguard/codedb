package org.archguard.codedb.build.task.api

interface Task : Comparable<Task> {
    var name: String
        get() = "name"
        set(value) = TODO()
    var description: String
        get() = "description"
        set(value) = TODO()
    var group: String
        get() = "group"
        set(value) = TODO()
    var dependsOn: Set<String>
        get() = setOf()
        set(value) = TODO()

    fun setDependsOn(dependsOnTasks: Iterable<*>)

    /**
     * Adds a dependency to this task.
     */
    fun dependsOn(vararg tables: Any): Task
}