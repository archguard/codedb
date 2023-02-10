package org.archguard.runner.serial

import io.kotest.matchers.shouldBe
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.decodeFromString
import org.archguard.runner.ActionManifestManager
import org.archguard.runner.pipeline.ActionDefinitionData
import org.junit.jupiter.api.Test
import java.io.File

class ScalarSerialTest {
    @Test
    fun `should convert yaml to json`() {
        val source = this.javaClass.classLoader.getResource("pipeline/serial/basic-input.yml")!!.path
        val actionManifest = ActionManifestManager().load(File(source).readText())

        val dist = this.javaClass.classLoader.getResource("pipeline/serial/basic-output.json")!!.path
        val output = File(dist).readText()

        Json.encodeToString(actionManifest) shouldBe Json.encodeToString(Json.decodeFromString<ActionDefinitionData>(output))
    }
}
