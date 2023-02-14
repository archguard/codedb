package org.archguard.action.checkout

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class GitCommandManagerTest {
    @Test
    @Disabled
    fun `test command manager git init`() {
        val gitCommandManager = GitCommandManager()
        val output = gitCommandManager.init()

        output.exitCode shouldBe 0
        output.stdout shouldBe  ""
    }

    @Test
    fun `test command manager git log`() {
        val gitCommandManager = GitCommandManager()
        val output = gitCommandManager.log()

        output.exitCode shouldBe 0
        output.stdout shouldNotBe  ""
    }

    @Test
    fun `test command manager git status`() {
        val gitCommandManager = GitCommandManager()
        val output = gitCommandManager.branchList(false)

        output shouldNotBe ""
    }
}