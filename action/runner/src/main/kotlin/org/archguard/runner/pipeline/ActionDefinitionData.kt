package org.archguard.runner.pipeline

import kotlinx.serialization.Serializable

@Serializable
data class ActionDefinitionData(
    var name: String = "",
    var description: String = "",
    var author: String? = "",
    var version: String? = "",
    var config: ActionConfig = ActionConfig(),
    val jobs: Map<String, ActionExecutionData> = mapOf()
)

enum class ActionExecutionType {
    Composite,
    Script,
    Plugin
}

