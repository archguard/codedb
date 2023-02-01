package org.archguard.codedb.automate.task

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class TaskRunnerTest {
    @Test
    @Disabled
    fun `run LocTask`() {
        val runner = TaskRunner()
        val output = runner.run()
        println(output)
    }
}