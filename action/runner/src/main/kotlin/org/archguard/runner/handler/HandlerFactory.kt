package org.archguard.runner.handler

import org.archguard.runner.pipeline.ActionExecutionData
import org.archguard.runner.pipeline.ActionExecutionType

class HandlerFactory {
    companion object {
        fun create(data: ActionExecutionData): Handler {
            return when(data.actionType) {
                ActionExecutionType.Composite -> CompositeActionHandler(data)
                else -> throw Exception("Unsupported action type: ${data.actionType}")
            }
        }
    }
}