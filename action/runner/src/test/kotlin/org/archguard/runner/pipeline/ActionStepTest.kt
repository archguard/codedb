package org.archguard.runner.pipeline

import io.kotest.matchers.shouldBe
import org.archguard.runner.serial.Scalar
import org.junit.jupiter.api.Test

class ActionStepTest {
    @Test
    fun `should convert command line`() {
        val step = ActionStep(
            name = "test",
            uses = "test",
            with = hashMapOf(
                "key2" to Scalar.List(listOf(Scalar.String("value1"), Scalar.String("value2"))),
                "key" to Scalar.String("value")
            )
        )
        val command = step.toCommandList()

        command.joinToString(" ") shouldBe "--key2 value1 --key2 value2 --key value"
    }

    @Test
    fun `should convert uppercase`() {
        val step = ActionStep(
            name = "test",
            uses = "test",
            with = hashMapOf(
                "server-url" to Scalar.String("value")
            )
        )
        val command = step.toCommandList()

        command.joinToString(" ") shouldBe "--server-url value"
    }

    @Test
    fun `should update variable`() {
        val step = ActionStep(
            name = "test",
            uses = "test",
            with = hashMapOf(
                "server-url" to Scalar.String("\${{ config.server.url }}")
            )
        )
        val command = step.toCommandList(ActionConfig(server = ActionServerConfig(url = "http://localhost:8084")))

        command.joinToString(" ") shouldBe "--server-url http://localhost:8084"
    }
}
