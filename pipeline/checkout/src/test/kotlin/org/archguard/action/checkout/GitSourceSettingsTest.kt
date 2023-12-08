package org.archguard.action.checkout

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class GitSourceSettingsTest {
    @Test
    fun `test from args for branch`() {
        val repository = "https://github.com/archguard/ddd-monolithic-code-sample"
        val branch = "master"

        val settings = GitSourceSettings(
            repository = repository,
            branch = branch,
        )

        executeGitCheckout(settings)
    }
}
