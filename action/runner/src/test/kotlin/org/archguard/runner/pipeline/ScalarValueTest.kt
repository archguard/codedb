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

    @Test
    fun `should convert array`() {
        // Numbers
        Scalar.from("[1, 2, 3]") shouldBe Scalar.Array(
            listOf(
                Scalar.Number(1.0),
                Scalar.Number(2.0),
                Scalar.Number(3.0)
            )
        )

        // String arrays
        Scalar.from("[\"1\", \"2\", \"3\"]") shouldBe Scalar.Array(
            listOf(
                Scalar.String("1"),
                Scalar.String("2"),
                Scalar.String("3")
            )
        )

        // single quoted string
        Scalar.from("['datamap']") shouldBe Scalar.Array(
            listOf(
                Scalar.String("datamap")
            )
        )
    }
}