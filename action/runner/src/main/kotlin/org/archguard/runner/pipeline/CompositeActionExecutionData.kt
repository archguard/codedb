package org.archguard.runner.pipeline

import kotlinx.serialization.Serializable

@Serializable
class CompositeActionExecutionData(
    override val type: ActionExecutionType = ActionExecutionType.Composite,
    val steps: List<ActionStep> = listOf(),
    // todo: support pre and post actions in future
    val pre: List<ActionDefinitionData> = listOf(),
    val post: List<ActionDefinitionData> = listOf()
) : ActionExecutionData() {

}
