package org.archguard.runner.pipeline

import kotlinx.serialization.Serializable

@Serializable
class CompositeActionExecutionJob(
    override val type: ActionExecutionType = ActionExecutionType.Composite,
    override var config: JobConfig = JobConfig(),
    override var steps: List<ActionStep> = listOf(),
    // todo: support pre-actions and post-actions in future
    val pre: List<ActionDefinitionData> = listOf(),
    val post: List<ActionDefinitionData> = listOf(),
) : ActionExecutionJob() {

}
