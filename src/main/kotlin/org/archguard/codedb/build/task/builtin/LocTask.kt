package org.archguard.codedb.build.task.builtin

import org.archguard.codedb.build.task.api.DefaultTask
import org.archguard.codedb.build.task.core.Input
import org.archguard.codedb.build.task.core.Output
import org.archguard.codedb.build.task.core.TaskAction

abstract class LocTask : DefaultTask() {
    @get:Input
    abstract val source: Any

    @get:Output
    abstract val output: Any

    @TaskAction
    fun run() {
        println("Running LocTask")
    }
}
