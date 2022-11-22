package org.archguard.codedb.automate.task

import org.archguard.codedb.automate.DefaultProject
import org.archguard.codedb.automate.internal.Project
import org.archguard.codedb.automate.task.api.Task
import org.archguard.codedb.automate.task.api.TaskCreatingProvider
import org.archguard.codedb.automate.task.api.TaskProvider
import org.archguard.codedb.automate.task.core.factory.DefaultTaskFactory
import org.archguard.codedb.automate.task.core.factory.TaskFactory
import org.archguard.codedb.automate.task.reflect.DirectInstantiator

class TaskContainer(
    private val project: Project = DefaultProject(),
    private val instantiator: DirectInstantiator = DirectInstantiator(),
    private val taskFactory: TaskFactory = DefaultTaskFactory()

) {
    private var objects: List<Task> = listOf()

    fun <T : Task> register(name: String?, type: Class<T>?, vararg constructorArgs: Any?): TaskProvider<T> {
        val provider = TaskCreatingProvider<T>()
        return provider
    }

    fun create(options: Map<String?, *>?): Task? {
        // process constructor args
        // configure task
        // create task
        // add task
        return null
    }

    fun addTask(task: Task) {
        this.objects += task
    }
}
