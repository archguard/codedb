package org.archguard.runner.runner

import org.archguard.runner.context.RunnerContext
import org.archguard.runner.handler.Handler
import org.archguard.runner.handler.HandlerFactory
import org.slf4j.LoggerFactory

class ActionRunner : RunnerService() {

    // todo: convert to handler and execute by type?
    fun run(context: RunnerContext) {
        val actionManager = ActionManager(context)

        // 1. load action definition
        val definitionData = actionManager.loadActions()
        // 2. build context
        context.registry = definitionData.env.registry.url

        // 3. create handler
        val handlers: List<Handler> = definitionData.jobs.flatMap { job ->
            val actionData = job.value

            actionManager.prepareActionsAsync(actionData)
            actionData.steps.map { step ->
                HandlerFactory.create(step, context)
            }
        }

        // 4. prepare actions
        handlers.forEach { handler ->
            handler.prepareExecution()
            logger.info("Run action: ${handler.getDisplayName()}, type: ${handler.javaClass.simpleName}")
            handler.runSync()
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(ActionRunner::class.java)
    }
}
