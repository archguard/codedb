package org.archguard.codedb.automate.task

import org.archguard.codedb.automate.builtin.LocTask
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

internal class TaskContainerTest {

    @Test
    @Disabled
    fun `should register task`() {
        val taskContainer = TaskContainer()
        val task = taskContainer.create("Hello", LocTask::class)
        task.run()
    }
}

