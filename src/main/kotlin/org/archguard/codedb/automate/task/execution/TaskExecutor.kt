package org.archguard.codedb.automate.task.execution

import org.archguard.codedb.automate.task.api.Task

abstract class TaskExecutor {
    fun execute(task: Task, context: TaskExecutionContext): TaskExecuterResult {
        return TaskExecuterResult()
    }
}