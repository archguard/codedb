package org.archguard.action.checkout

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.contain
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class GitCommandManagerTest {

    private lateinit var gitCommandManager: GitCommandManager

    @BeforeEach
    fun setup() {
        gitCommandManager = GitCommandManager()
    }

    @Test
    fun `test command manager git log`() {
        val output = gitCommandManager.log()

        output.exitCode shouldBe 0
        output.stdout shouldNotBe  ""
    }

    @Test
    fun `test command manager git status`() {
        val output = gitCommandManager.branchList(false)

        output shouldNotBe ""
    }

    @Test
    fun `test command manager git branch list`() {
        val output = gitCommandManager.branchList(false)

        output.contains("master") shouldBe true

        val branchList = gitCommandManager.parseBranchList(listOf("refs/heads/master"))
        branchList.contains("refs/heads/master") shouldBe false
        branchList.contains("master") shouldBe true
    }

    @Test
    fun `test parse default branch`() {
        val output = gitCommandManager.parseDefaultBranch("""ref: refs/heads/master	HEAD
13f70a2588e474f53a0b0059d60d2f4bedb2eb8a	HEAD""")

        output shouldBe "refs/heads/master"
    }
}