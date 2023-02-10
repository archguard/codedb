package org.archguard.runner.pipeline

import kotlinx.serialization.Serializable

@Serializable
sealed class ActionExecutionData {
    abstract val name: String
    abstract val config: JobConfig
    abstract val steps: List<ActionStep>
    abstract val actionType: ActionExecutionType
}

@Serializable
data class ScriptActionExecutionData(
    override var name: String = "",
    override var config: JobConfig = JobConfig(),
    override var steps: List<ActionStep> = listOf(),
    override val actionType: ActionExecutionType = ActionExecutionType.Script,
    val script: String = ""
) : ActionExecutionData()

@Serializable
data class CompositeActionExecutionData(
    override var name: String = "",
    override var config: JobConfig = JobConfig(),
    override var steps: List<ActionStep> = listOf(),
    override val actionType: ActionExecutionType = ActionExecutionType.Composite,
    // todo: support pre-actions and post-actions in future
    val pre: List<ActionDefinitionData> = listOf(),
    val post: List<ActionDefinitionData> = listOf(),
) : ActionExecutionData() {

}