package org.archguard.runner.serial

import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.YamlNode
import com.charleskorn.kaml.yamlMap
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class ScalarTest {
    @Test
    fun `should convert scalar value`() {
        Scalar.fromString("value") shouldBe Scalar.String("value")
        Scalar.fromString("true") shouldBe Scalar.Boolean(true)

        Scalar.fromString("1") shouldBe Scalar.Number(1.0)
    }

    @Test
    fun `should convert array`() {
        val result = Yaml.default.parseToYamlNode("value: [1, 2, 3]")
        val scalar = result.yamlMap.get<YamlNode>("value")!!

        // Numbers
        Scalar.from(scalar) shouldBe Scalar.Array(
            listOf(
                Scalar.Number(1.0),
                Scalar.Number(2.0),
                Scalar.Number(3.0)
            )
        )

        val result2 = Yaml.default.parseToYamlNode("value: [\"1\", \"2\", \"3\"]")
        val scalar2 = result2.yamlMap.get<YamlNode>("value")!!

        Scalar.from(scalar2) shouldBe Scalar.Array(
            listOf("1.0", "2.0", "3.0").map { Scalar.Number(it.toDouble()) }
        )
    }
}
