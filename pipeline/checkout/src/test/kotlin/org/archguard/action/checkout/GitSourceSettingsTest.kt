package org.archguard.action.checkout

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class GitSourceSettingsTest {
    @Test
    fun `test from args for branch`() {
        val repository = "https://github.com/archguard/codedb"
        val branch = "master"

        val settings = GitSourceSettings.fromArgs(arrayOf("--repository", repository, "--branch", branch))
        settings.workdir = ".tmp"
        settings.repositoryPath = ".tmp" + java.io.File.separator + repository.substringAfterLast("/")

        val commandManager = GitCommandManager(settings.repositoryPath)
        doCheckout(commandManager, settings)
    }
}
