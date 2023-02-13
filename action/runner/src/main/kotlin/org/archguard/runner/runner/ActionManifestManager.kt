package org.archguard.runner.runner

import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.YamlList
import com.charleskorn.kaml.YamlNode
import com.charleskorn.kaml.yamlMap
import kotlinx.serialization.decodeFromString
import org.archguard.runner.pipeline.ActionDefinitionData
import org.archguard.runner.pipeline.ActionStep
import org.archguard.runner.pipeline.CompositeActionExecutionData
import org.archguard.runner.pipeline.JobConfig
import org.archguard.runner.serial.Scalar
import org.archguard.runner.serial.flatString
import org.archguard.runner.serial.from
import org.archguard.runner.serial.objectValue
import org.archguard.runner.serial.stringify


/**
 * parse action manifest or json schema
 */
class ActionManifestManager {
    /**
     * load action manifest string
     */
    fun load(manifestContent: String): ActionDefinitionData {
        val result = Yaml.default.parseToYamlNode(manifestContent)

        val jobs = result.yamlMap.get<YamlNode>("jobs")?.yamlMap?.entries?.let {
            it.map { entry ->
                val yamlMap = entry.value.yamlMap

                val jobConfig = yamlMap.get<YamlNode>("config")
                val stepItems = yamlMap.get<YamlList>("steps")?.items

                // todo: support script action
                CompositeActionExecutionData(
                    name = entry.key.content,
                    steps = stepItems?.map(::convertStep) ?: listOf(),
                    config = jobConfig?.let(::convertJobConfig) ?: JobConfig()
                )
            }
        }

        return ActionDefinitionData(
            name = result.flatString("name"),
            config = result.objectValue("config"),
            description = result.flatString("description"),
            author = result.flatString("author"),
            version = result.flatString("version"),
            jobs = jobs?.associate { it.name to it } ?: mapOf()
        )
    }

    private fun convertJobConfig(config: YamlNode) = Yaml.default.decodeFromString<JobConfig>(config.stringify())

    private fun convertStep(node: YamlNode): ActionStep {
        val actionStep = ActionStep()
        node.yamlMap.entries.forEach { entry ->
            when (entry.key.content) {
                "name" -> {
                    actionStep.name = entry.value.stringify()
                }

                "description" -> {
                    actionStep.description = entry.value.stringify()
                }

                "enabled" -> {
                    actionStep.enabled = entry.value.contentToString().toBoolean()
                }

                "uses" -> {
                    actionStep.uses = entry.value.stringify()
                }

                "with" -> {
                    entry.value.yamlMap.entries.forEach { prop ->
                        actionStep.with[prop.key.content]  = Scalar.from(prop.value)
                    }
                }
            }
        }

        return actionStep
    }
}
