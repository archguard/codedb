package org.archguard.runner.runner

class JobRunner: RunnerService() {
    val stepRunner = StepRunner()
    fun run() {
        trace.info("JobRunner run")

        stepRunner.run {

        }
    }
}