package org.archguard.runner.pipeline

import kotlinx.serialization.Serializable

@Serializable
class ActionDefinitionData(
    var name: String = "",
    var description: String = "",
    var author: String? = "",
    var version: String? = "",
    var config: ActionConfig = ActionConfig(),
    val jobs: Map<String, ActionExecutionJob> = mapOf()
) {
}

@Serializable
abstract class ActionExecutionJob {
    abstract val config: JobConfig
    abstract val steps: List<ActionStep>
    abstract val type: ActionExecutionType
}

enum class ActionExecutionType {
    Composite,
    Script,
    Plugin
}

