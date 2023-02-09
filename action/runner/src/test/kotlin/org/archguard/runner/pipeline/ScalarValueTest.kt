package org.archguard.runner.pipeline

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class ScalarValueTest {
    @Test
    fun `should convert scalar value`() {
        Scalar.from("value") shouldBe Scalar.String("value")
    }
}