package org.archguard.runner

import org.archguard.runner.context.RunnerContext

class ActionManager {
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

