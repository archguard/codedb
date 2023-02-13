package org.archguard.runner.runner

import org.archguard.runner.context.RunnerContext
import org.archguard.runner.handler.HandlerFactory

class ActionRunner: RunnerService() {

    // todo: convert to handler and execute by type?
    fun run(context: RunnerContext) {
        val actionManager = ActionManager(context)

        // 1. load action definition
        val definitionData = actionManager.loadActions()
        // 2. build context

        // 3. create handler
        // 4. prepare actions
        val handlers = definitionData.jobs.map { job ->
            // todo: move to handler ??
            actionManager.prepareActionsAsync(job.value)
            HandlerFactory.create(job.value)
        }

        handlers.forEach { handler ->
            handler.runSync()
        }
    }
}
