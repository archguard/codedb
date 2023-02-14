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
data class ActionConfig(
    var metric: Boolean = false,
    val plugin: PluginConfig = PluginConfig(),
    val server: ActionServerConfig = ActionServerConfig()
)

@Serializable
data class PluginConfig(
    // string representation of the plugin registry
    val registry: String = "",
)

@Serializable
data class JobConfig(
    val server: ActionServerConfig = ActionServerConfig(),
    val displayName: String = "",
)

@Serializable
data class ActionServerConfig(
    var url: String = ""
)
