package org.archguard.codedb.automate.task

import org.archguard.core.DefaultProject
import org.archguard.codedb.automate.task.api.AbstractTask
import org.archguard.codedb.automate.task.core.Input
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.primaryConstructor

class TaskRunner {
    fun run(): Input? {
        val instance = DefaultProject::class.primaryConstructor!!.call("demo", "", "")

        // todo: add task collections
        val runnerKClass = AbstractTask::class
        val tasks = runnerKClass.findAnnotation<Input>()
        return tasks
    }
}
