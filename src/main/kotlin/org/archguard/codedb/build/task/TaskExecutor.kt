package org.archguard.codedb.build.task

import org.archguard.codedb.build.task.api.Task

abstract class TaskExecutor {
    fun execute(task: Task, context: TaskExecutionContext): TaskExecuterResult {
        return TaskExecuterResult()
    }
}