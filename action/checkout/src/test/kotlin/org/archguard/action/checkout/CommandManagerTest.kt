package org.archguard.action.checkout

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class CommandManagerTest {
    @Test
    @Disabled
    fun `test command manager git init`() {
        val commandManager = CommandManager()
        val output = commandManager.init()

        output.exitCode shouldBe 0
        output.stdout shouldBe  ""
    }

    @Test
    fun `test command manager git log`() {
        val commandManager = CommandManager()
        val output = commandManager.log()

        output.exitCode shouldBe 0
        output.stdout shouldNotBe  ""
    }
}