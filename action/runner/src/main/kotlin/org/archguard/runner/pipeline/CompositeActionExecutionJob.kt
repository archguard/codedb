package org.archguard.runner.pipeline

import kotlinx.serialization.Serializable

@Serializable
class CompositeActionExecutionJob(
    override val type: ActionExecutionType = ActionExecutionType.Composite,
    override val config: JobConfig = JobConfig(),
    override val steps: List<ActionStep> = listOf(),
    // todo: support pre-actions and post-actions in future
    val pre: List<ActionDefinitionData> = listOf(),
    val post: List<ActionDefinitionData> = listOf(),
) : ActionExecutionJob() {

}
