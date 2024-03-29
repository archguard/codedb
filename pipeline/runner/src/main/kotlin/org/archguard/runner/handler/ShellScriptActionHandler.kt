package org.archguard.runner.handler

import org.archguard.action.exec.Command
import org.archguard.runner.context.RunnerContext
import org.archguard.runner.pipeline.ActionStep

class ShellScriptActionHandler(
    private val step: ActionStep,
    val context: RunnerContext,
    val name: String
) : Handler {
    override fun getDisplayName(): String = name
    override fun runSync() {

    }
}
