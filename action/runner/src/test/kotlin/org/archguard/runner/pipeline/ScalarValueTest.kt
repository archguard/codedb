package org.archguard.runner.pipeline

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class ScalarValueTest {
    @Test
    fun `should convert scalar value`() {
        Scalar.from("value") shouldBe Scalar.String("value")
        Scalar.from("true") shouldBe Scalar.Boolean(true)

        Scalar.from("1") shouldBe Scalar.Number(1.0)
    }
}