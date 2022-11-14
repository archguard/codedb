package org.archguard.codedb.build.task

import org.archguard.codedb.build.task.api.AbstractTask
import org.archguard.codedb.build.task.core.Input
import kotlin.reflect.full.findAnnotation

class TaskRunner {
    fun run(): Input? {
        val runnerKClass = AbstractTask::class
        val tasks = runnerKClass.findAnnotation<Input>()
        return tasks
    }
}
