package org.archguard.runner.handler

import org.archguard.runner.context.RunnerContext
import org.archguard.runner.pipeline.ActionStep

class CommandActionHandler(
    private val step: ActionStep,
    val context: RunnerContext,
    val name: String
) : Handler {
    override fun getDisplayName(): String = name

    override fun runSync() {

    }
}

