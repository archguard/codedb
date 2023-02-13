package org.archguard.runner

import org.archguard.runner.runner.JobRunner
import org.archguard.runner.runner.RunnerService

class Worker: RunnerService() {
    private val jobRunner: JobRunner = JobRunner()
    fun run() {
        // 1. create context
        // 2. create jobRunner
        jobRunner.run {

        }
        // 3. run jobRunner
    }
}