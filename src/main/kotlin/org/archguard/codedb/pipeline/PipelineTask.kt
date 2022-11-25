package org.archguard.codedb.pipeline

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.archguard.codedb.automate.task.core.Input

// todo: add pipeline task
class PipelineTask(val output: Channel<String>,) {
    fun run() {
        println("Running PipelineTask")
    }

    @Input
    suspend fun input(src: Channel<String>) = coroutineScope {
        launch {
            for (s in src) {
                // do something, then output it
                output.send(s)
            }
        }
    }
}