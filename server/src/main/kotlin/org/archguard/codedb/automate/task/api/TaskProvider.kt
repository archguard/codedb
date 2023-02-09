package org.archguard.codedb.automate.task.api

import org.archguard.core.internal.Action

interface TaskProvider<T : Task> {
    fun configure(action: Action<in T>)
    fun getName(): String
}


class TaskCreatingProvider<I : Task> : TaskProvider<I> {
    private val name: String? = null

    override fun configure(action: Action<in I>) {
    }

    override fun getName(): String {
        return name ?: ""
    }
}