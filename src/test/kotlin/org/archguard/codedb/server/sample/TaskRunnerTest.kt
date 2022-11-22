package org.archguard.codedb.server.sample

import org.archguard.codedb.automate.task.TaskRunner
import org.junit.jupiter.api.Test

class TaskRunnerTest {
    @Test
    fun `run LocTask`() {
        val runner = TaskRunner()
        val output = runner.run()
        println(output)
    }
}