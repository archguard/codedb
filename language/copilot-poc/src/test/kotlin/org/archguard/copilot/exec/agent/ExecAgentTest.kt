package org.archguard.copilot.exec.agent

import org.junit.jupiter.api.Test

class ExecAgentTest {
    @Test
    fun demo() {
        exec {
            workspace("language/copilot-poc")

            group("org.archguard.copilot.exec.agent")

            archStyle("ddd")
        }
    }
}