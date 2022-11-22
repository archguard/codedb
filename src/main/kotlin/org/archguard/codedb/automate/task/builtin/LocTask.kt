package org.archguard.codedb.automate.task.builtin

import org.archguard.codedb.automate.task.DefaultTask
import org.archguard.codedb.automate.task.core.CacheableTask
import org.archguard.codedb.automate.task.core.Input
import org.archguard.codedb.automate.task.core.Output
import org.archguard.codedb.automate.task.core.TaskAction

@CacheableTask
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
