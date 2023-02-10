package org.archguard.runner.context

import org.archguard.runner.JobRunner
import org.archguard.runner.RunnerService

class ActionManager: RunnerService() {
    fun prepareActionsAsync(context: RunnerContext) {
        // get registered
        downloadAction(context)
    }

    //
    private fun downloadAction(context: RunnerContext) {
        val jobRunner = JobRunner()
        jobRunner.downloadAction()
    }
}

