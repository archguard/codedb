package org.archguard.runner.handler

import io.kotest.matchers.types.shouldBeTypeOf
import org.archguard.runner.context.RunnerContext
import org.archguard.runner.pipeline.ActionStep
import org.junit.jupiter.api.Test

class HandlerFactorTest {
    @Test
    fun `handler type shell script`() {
        val handler = HandlerFactory.create(
            ActionStep(
                name = "test",
                run = "test.sh"
            ), RunnerContext()
        )

        handler.shouldBeTypeOf<ShellScriptActionHandler>()
    }

    @Test
    fun `handler type command`() {
        val handler = HandlerFactory.create(
            ActionStep(
                name = "test",
                run = "npm ci"
            ), RunnerContext()
        )

        handler.shouldBeTypeOf<CommandActionHandler>()
    }

    @Test
    fun `handler type kotlin script`() {
        val handler = HandlerFactory.create(
            ActionStep(
                name = "test",
                run = "test.kts"
            ), RunnerContext()
        )

        handler.shouldBeTypeOf<KotlinScriptActionHandler>()
    }
}