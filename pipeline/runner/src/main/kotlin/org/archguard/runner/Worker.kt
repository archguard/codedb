package org.archguard.runner

import org.archguard.runner.context.RunnerContext
import org.archguard.runner.runner.JobRunner
import org.archguard.runner.runner.RunnerService

class Worker: RunnerService() {
    private val jobRunner: JobRunner = JobRunner()
    fun run(configFile: String) {
        val runnerContext = RunnerContext()
        runnerContext.manifestYmlPath  = configFile
        jobRunner.run(runnerContext)
    }
}