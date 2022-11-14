package org.archguard.codedb.build.task

import org.archguard.codedb.build.task.api.DefaultTask
import org.archguard.codedb.build.task.core.Input
import kotlin.reflect.full.findAnnotation

class TaskRunner {
    fun run(): Input? {
        val runnerKClass = DefaultTask::class
        val tasks = runnerKClass.findAnnotation<Input>()
        return tasks
    }
}
