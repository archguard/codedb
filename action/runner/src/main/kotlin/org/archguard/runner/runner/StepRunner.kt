package org.archguard.runner.runner

class StepRunner: RunnerService() {
    val actionRunner = ActionRunner()
    fun run() {
        trace.info("StepRunner run")
        actionRunner.run {

        }
    }
}