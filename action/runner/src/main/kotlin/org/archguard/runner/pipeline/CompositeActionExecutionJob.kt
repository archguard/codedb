package org.archguard.runner.pipeline

import kotlinx.serialization.Serializable

@Serializable
data class CompositeActionExecutionJob(
    override var name: String = "",
    override var config: JobConfig = JobConfig(),
    override var steps: List<ActionStep> = listOf(),
    override val actionType: ActionExecutionType = ActionExecutionType.Composite,
    // todo: support pre-actions and post-actions in future
    val pre: List<ActionDefinitionData> = listOf(),
    val post: List<ActionDefinitionData> = listOf(),
) : ActionExecutionJob() {

}
