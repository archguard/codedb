package org.archguard.runner.pipeline

import kotlinx.serialization.Serializable

@Serializable
class ActionStep(
    val name: String = "",
    val description: String = "",
    val enabled: Boolean = true,
    val uses: String = "",
    val with: Map<String, String> = mapOf()
) {
}
