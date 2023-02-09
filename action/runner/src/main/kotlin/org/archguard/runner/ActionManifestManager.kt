package org.archguard.runner

import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.YamlList
import com.charleskorn.kaml.YamlNode
import com.charleskorn.kaml.YamlScalar
import com.charleskorn.kaml.yamlMap
import com.charleskorn.kaml.yamlScalar
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.archguard.runner.pipeline.ActionDefinitionData
import org.archguard.runner.pipeline.ActionStep
import org.archguard.runner.pipeline.CompositeActionExecutionJob
import org.archguard.runner.pipeline.JobConfig
import org.archguard.runner.pipeline.Scalar


/**
 * parse action manifest or json schema
 */
class ActionManifestManager {
    fun load(manifest: String): ActionDefinitionData {
        val result = Yaml.default.parseToYamlNode(manifest)

        val jobs = result.yamlMap.get<YamlNode>("jobs")?.yamlMap?.entries?.let { it ->
            it.map { entry ->
                val job = CompositeActionExecutionJob()

                val yamlMap = entry.value.yamlMap
                job.config = yamlMap.get<YamlNode>("config")?.let { config ->
                    Yaml.default.decodeFromString<JobConfig>(config.contentToString())
                } ?: JobConfig()

                job.steps = yamlMap.get<YamlList>("steps")?.items?.map { it ->
                    val actionStep = ActionStep()
                    it.yamlMap.entries.forEach { entry ->
                        when (entry.key.content) {
                            "uses" -> {
                                actionStep.uses = entry.value.contentToString()
                            }

                            "with" -> {
                                entry.value.yamlMap.entries.forEach { prop ->
                                    when (prop.value.javaClass) {
                                        YamlScalar::class.java -> {
                                            actionStep.with[prop.key.content] =
                                                Scalar.from(prop.value.contentToString())
                                        }

                                        YamlList::class.java -> {
                                            val strings = prop.value.contentToString().split(",")
                                            actionStep.with[prop.key.content] =
                                                Scalar.Array(strings.map { str -> Scalar.String(str) })
                                        }

                                        else -> {}
                                    }
                                }
                            }
                        }
                    }

                    actionStep
                } ?: listOf()

                job
            }
        }

        println(Json.encodeToString(jobs))

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
