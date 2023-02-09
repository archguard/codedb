package org.archguard.runner

import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.YamlNode
import com.charleskorn.kaml.yamlMap
import com.charleskorn.kaml.yamlScalar
import kotlinx.serialization.decodeFromString
import org.archguard.runner.pipeline.ActionDefinitionData


/**
 * parse action manifest or json schema
 */
class ActionManifestManager {
    fun load(manifest: String): ActionDefinitionData {
        val result = Yaml.default.parseToYamlNode(manifest)

        return ActionDefinitionData(
            name = result.flatString("name"),
            config = result.objectValue("config"),
            description = result.flatString("description"),
            author = result.flatString("author"),
            version = result.flatString("version"),
        )
    }
}

private inline fun <reified T> YamlNode.objectValue(name: String): T {
    val node = this.yamlMap.get<YamlNode>(name)?.contentToString()
    return Yaml.default.decodeFromString(node ?: "")
}

private fun YamlNode.flatString(name: String): String {
    return this.yamlMap.get<YamlNode>(name)?.yamlScalar?.content ?: ""
}
