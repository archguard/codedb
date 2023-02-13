package org.archguard.runner.handler

import org.archguard.runner.context.RunnerContext
import org.archguard.runner.pipeline.ActionExecutionType
import org.archguard.runner.pipeline.ActionStep

class HandlerFactory {
    companion object {
        fun create(data: ActionStep, context: RunnerContext): Handler {
            return CompositeActionHandler(data, context)
        }
    }
}