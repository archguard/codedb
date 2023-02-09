package org.archguard.runner.pipeline

// public sealed class ActionDefinitionData
// {
//     public string Name { get; set; }
//
//     public string Description { get; set; }
//
//     public MappingToken Inputs { get; set; }
//
//     public ActionExecutionData Execution { get; set; }
//
//     public Dictionary<String, String> Deprecated { get; set; }
// }

class ActionDefinitionData(
    var name: String = "",
    var description: String = "",
    val execution: ActionExecutionData = CompositeActionExecutionData(),
    val deprecated: Map<String, String> = mapOf()
) {
    var author: String? = null
    var version: String? = null
}

abstract class ActionExecutionData {
    abstract val type: ActionExecutionType
}

enum class ActionExecutionType {
    Composite,
    Script,
    Plugin
}

class CompositeActionExecutionData(
    override val type: ActionExecutionType = ActionExecutionType.Composite,
    val steps: List<ActionStep> = listOf(),
    val pre: List<ActionDefinitionData> = listOf(),
    val post: List<ActionDefinitionData> = listOf()
) : ActionExecutionData() {

}