package org.archguard.codedb.automate.task

import org.archguard.core.DefaultProject
import org.archguard.core.Project
import org.archguard.codedb.automate.task.api.Task
import org.archguard.codedb.automate.task.api.TaskCreatingProvider
import org.archguard.codedb.automate.task.api.TaskProvider
import org.archguard.codedb.automate.task.core.factory.DefaultTaskFactory
import org.archguard.codedb.automate.task.core.factory.TaskFactory
import org.archguard.codedb.automate.task.core.factory.TaskIdentity
import org.archguard.codedb.automate.task.reflect.DirectInstantiator
import kotlin.reflect.KClass

class TaskContainer(
    private val project: Project = DefaultProject(),
    private val instantiator: DirectInstantiator = DirectInstantiator(),
    private val taskFactory: TaskFactory = DefaultTaskFactory()

) {
    private var objects: List<Task> = listOf()

    fun <T : Task> register(name: String?, type: KClass<T>?, vararg constructorArgs: Any?): TaskProvider<T> {
        val provider = TaskCreatingProvider<T>()
        return provider
    }

    fun <T : Task> create(name: String, type: KClass<T>, vararg constructorArgs: Any): T {
        val identity = TaskIdentity(type, name, project)
        val createdTask: T? = taskFactory.create(identity, constructorArgs as Array<Any>)
        // todo: handle dependsOn
        return createdTask?: throw RuntimeException("Task creation failed")
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
