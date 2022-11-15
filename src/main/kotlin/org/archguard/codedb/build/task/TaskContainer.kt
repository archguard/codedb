package org.archguard.codedb.build.task

import org.archguard.codedb.build.internal.Action
import org.archguard.codedb.build.task.api.Task

class TaskContainer {
    private var objects: List<Task> = listOf()

    fun register(name: String?, configurationAction: Action<in Task?>?) {

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
