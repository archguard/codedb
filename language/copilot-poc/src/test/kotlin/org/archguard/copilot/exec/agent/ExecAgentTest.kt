package org.archguard.copilot.exec.agent

import org.junit.jupiter.api.Test

class ExecAgentTest {
    @Test
    fun demo() {
        exec {
            workspace("language/copilot-poc")
            archStyle("dd")
            createPackage("org.archguard.copilot.exec.agent")
        }
    }
}