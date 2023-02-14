package org.archguard.runner.handler

import org.archguard.action.exec.ActionExec
import org.archguard.runner.context.RunnerContext
import org.archguard.runner.pipeline.ActionStep
import java.io.File

class ShellActionHandler(
    private val step: ActionStep,
    val context: RunnerContext
): Handler {
    override fun runSync() {
        val args: List<String> = listOf("sh", "-c", step.uses) + step.toCommandList(context.actionEnv)
        ActionExec().run(args, File(context.pluginDirectory))
    }
}
