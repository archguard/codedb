package org.archguard.action.exec

import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test

class CommandTest {
    @Test
    fun `test command manager git`() {
        var stdout = ""
        val options = ExecOptions(
            cwd = ".",
            listeners = object : ExecListeners {
                override fun stdout(data: String) {
                    stdout += data
                }
            }
        )

        Command().exec("git", listOf("status"), options)
        stdout shouldNotBe ""
    }
}