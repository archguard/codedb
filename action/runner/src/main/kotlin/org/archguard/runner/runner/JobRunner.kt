package org.archguard.runner.runner

import org.archguard.runner.context.RunnerContext

class JobRunner : RunnerService() {
    private val stepRunner = StepRunner()

    fun run(context: RunnerContext) {
        trace.info("JobRunner run")

        stepRunner.run(context)
    }
}