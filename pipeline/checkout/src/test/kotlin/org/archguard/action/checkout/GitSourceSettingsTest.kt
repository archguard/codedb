package org.archguard.action.checkout

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class GitSourceSettingsTest {
    @Test
    fun `test from args for branch`() {
        val repository = "https://github.com/archguard/codedb"
        val branch = "master"

        val settings = GitSourceSettings(
            repository = repository,
            branch = branch,
            workdir = ".tmp",
        )

        val commandManager = GitCommandManager(settings.repositoryPath)
        doCheckout(commandManager, settings)
    }
}
