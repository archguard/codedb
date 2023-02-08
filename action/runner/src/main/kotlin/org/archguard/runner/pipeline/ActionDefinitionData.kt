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
    val name: String,
    val description: String,
    val execution: ActionExecutionData,
    val deprecated: Map<String, String>
) {

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
    val steps: List<ActionStep>,
    val pre: List<ActionDefinitionData>,
    val post: List<ActionDefinitionData>
) : ActionExecutionData() {

}