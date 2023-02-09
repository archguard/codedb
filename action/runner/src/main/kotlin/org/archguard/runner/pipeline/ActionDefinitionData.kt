package org.archguard.runner.pipeline

import kotlinx.serialization.Serializable

@Serializable
class ActionDefinitionData(
    var name: String = "",
    var description: String = "",
    var author: String? = "",
    var version: String? = "",
    var config: ActionConfig = ActionConfig(),
    val execution: ActionExecutionData = CompositeActionExecutionData()
) {
}

@Serializable
abstract class ActionExecutionData {
    abstract val type: ActionExecutionType
}

enum class ActionExecutionType {
    Composite,
    Script,
    Plugin
}

