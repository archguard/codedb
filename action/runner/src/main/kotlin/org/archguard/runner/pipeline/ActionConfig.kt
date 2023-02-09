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
class ActionServerConfig(
    var url: String = ""
)
