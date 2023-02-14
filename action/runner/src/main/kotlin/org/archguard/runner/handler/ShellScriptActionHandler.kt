package org.archguard.runner.handler

import org.archguard.action.exec.Command
import org.archguard.runner.context.RunnerContext
import org.archguard.runner.pipeline.ActionStep
import java.io.File

class ShellScriptActionHandler(
    private val step: ActionStep,
    val context: RunnerContext
): Handler {
    override fun runSync() {
        val args: List<String> = listOf("sh", "-c", step.uses) + step.toCommandList(context.actionEnv)
        Command().run(args, File(context.pluginDirectory))
    }
}
