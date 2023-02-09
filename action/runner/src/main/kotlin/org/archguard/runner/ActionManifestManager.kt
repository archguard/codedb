package org.archguard.runner

import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.YamlList
import com.charleskorn.kaml.YamlNode
import com.charleskorn.kaml.yamlMap
import com.charleskorn.kaml.yamlScalar
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.archguard.runner.pipeline.ActionConfig
import org.archguard.runner.pipeline.ActionDefinitionData
import org.archguard.runner.pipeline.ActionExecutionJob
import org.archguard.runner.pipeline.ActionStep
import org.archguard.runner.pipeline.JobConfig


/**
 * parse action manifest or json schema
 */
class ActionManifestManager {
    fun load(manifest: String): ActionDefinitionData {
        val result = Yaml.default.parseToYamlNode(manifest)

        result.yamlMap.get<YamlNode>("jobs")?.yamlMap?.entries?.let { it ->
            it.forEach { entry ->
                val yamlMap = entry.value.yamlMap
                val config = yamlMap.get<YamlNode>("config")?.let { config ->
                    Yaml.default.decodeFromString<JobConfig>(config.contentToString())
                }

                yamlMap.get<YamlList>("steps")?.items?.forEach {
//                    val step = Yaml.default.decodeFromString<ActionStep>(it.contentToString())
//                    println(step)
                }
            }
        }

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
