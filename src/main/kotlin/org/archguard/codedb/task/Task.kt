package org.archguard.codedb.task

interface Task : Comparable<Task> {
    val name: String
    val description: String
    val group: String
    val dependsOn: Set<String>

    fun setDependsOn(dependsOnTasks: Iterable<*>)
    fun dependsOn(vararg paths: Any): Task
}