package org.archguard.runner.pipeline

import kotlinx.serialization.Serializable

/**
 * examples:
 * ```yaml
 * config:
 *   metric: true
 *   server:
 *     url: http://localhost:8084
 * ```
 */

@Serializable
class ActionConfig(
    var metric: Boolean = false,
    val server: ActionServerConfig = ActionServerConfig()
) {
}

@Serializable
class JobConfig(
    val server: ActionServerConfig = ActionServerConfig(),
    val displayName: String = "",
    val languages: List<String> = listOf(),
    val features: List<String> = listOf(),
) {

}

@Serializable
class ActionServerConfig(
    var url: String = ""
)
