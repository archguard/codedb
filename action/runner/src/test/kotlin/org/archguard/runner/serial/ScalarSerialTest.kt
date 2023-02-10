package org.archguard.runner.serial

import com.charleskorn.kaml.Yaml
import io.kotest.matchers.shouldBe
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.archguard.runner.ActionManifestManager
import org.archguard.runner.pipeline.ActionDefinitionData
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.io.File

class ScalarSerialTest {
    private val yaml = Yaml.default

    @Test
    @Disabled
    fun `should convert scalar value back`() {
        val input = "value: [1, 2, 3]"

        val result = yaml.parseToYamlNode(input)
        val value = Scalar.from(result)

        yaml.encodeToString(value) shouldBe yaml.encodeToString(yaml.decodeFromString<Map<String, List<Double>>>(input))
    }

    @Test
    @Disabled
    fun `should convert yaml to json`() {
        val source = this.javaClass.classLoader.getResource("pipeline/serial/basic-input.yml")!!.path
        val actionManifest = ActionManifestManager().load(File(source).readText())

        val dist = this.javaClass.classLoader.getResource("pipeline/serial/basic-output.json")!!.path
        val output = File(dist).readText()

        val expectedData = Json.decodeFromString<ActionDefinitionData>(output)

        println("expectedData: $expectedData")
        val jsonObject = Json.encodeToString(expectedData)
        Json.encodeToString(actionManifest) shouldBe jsonObject
    }
}
