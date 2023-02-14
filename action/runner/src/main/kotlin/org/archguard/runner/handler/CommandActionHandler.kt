package org.archguard.runner.handler

import org.archguard.action.exec.Command
import org.archguard.runner.context.RunnerContext
import org.archguard.runner.pipeline.ActionStep
import java.io.File

class CommandActionHandler(
    private val step: ActionStep,
    val context: RunnerContext
): Handler {
    override fun runSync() {
        Command().run(step.run.split("\\s"), File(context.pluginDirectory))
    }
}

