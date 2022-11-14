package org.archguard.codedb.server.sample

import org.archguard.codedb.build.task.TaskRunner
import org.junit.jupiter.api.Test

class TaskRunnerTest {
    @Test
    fun `run LocTask`() {
        val runner = TaskRunner()
        val output = runner.run()
        println(output)
    }
}