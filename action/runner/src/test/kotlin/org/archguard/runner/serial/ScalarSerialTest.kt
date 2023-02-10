package org.archguard.runner.serial

import com.charleskorn.kaml.PolymorphismStyle
import com.charleskorn.kaml.SequenceStyle
import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.YamlConfiguration
import io.kotest.matchers.equality.shouldBeEqualToComparingFields
import io.kotest.matchers.shouldBe
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import org.archguard.runner.ActionManifestManager
import org.archguard.runner.pipeline.ActionDefinitionData
import org.junit.jupiter.api.Test
import java.io.File

class ScalarSerialTest {
    private val yaml = Yaml.default

    @Test
    fun `should convert scalar value back`() {
        val input = "value: [1, 2, 3]"

        val result = yaml.parseToYamlNode(input)
        val value = Scalar.from(result)

        yaml.encodeToString(value) shouldBe yaml.encodeToString(yaml.decodeFromString<Map<String, List<Double>>>(input))
    }

    @Test
    fun `should convert yaml to yaml to yaml`() {
        val source = this.javaClass.classLoader.getResource("pipeline/serial/basic-input.yml")!!.path
        val firstOutput: ActionDefinitionData = ActionManifestManager().load(File(source).readText())

        val output = Yaml(configuration = YamlConfiguration(
            encodeDefaults = false,
            polymorphismStyle = PolymorphismStyle.Property,
        )).encodeToString(firstOutput)

        val secondParsedOutput: ActionDefinitionData = ActionManifestManager().load(output)

        firstOutput.shouldBeEqualToComparingFields(secondParsedOutput)
    }
}
