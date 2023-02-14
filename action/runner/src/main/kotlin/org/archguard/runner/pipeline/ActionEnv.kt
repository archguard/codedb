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
data class ActionEnv(
    var metric: Boolean = false,
    val registry: RegistryConfig = RegistryConfig(),
    val server: ActionServerConfig = ActionServerConfig()
)

@Serializable
data class RegistryConfig(
    // string representation of the plugin registry
    val url: String = "",
    val local: Boolean = false,
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
