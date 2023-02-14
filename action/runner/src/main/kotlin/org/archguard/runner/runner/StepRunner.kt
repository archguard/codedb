package org.archguard.runner.runner

import org.archguard.runner.context.RunnerContext

class StepRunner: RunnerService() {
    private val actionRunner = ActionRunner()

    fun run(content: RunnerContext) {
        trace.info("StepRunner run")
        actionRunner.run(content)
    }
}