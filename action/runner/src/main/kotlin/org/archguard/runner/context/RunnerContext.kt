package org.archguard.runner.context

import com.google.gson.stream.JsonToken

/**
 * Runner context
 */
open class RunnerContext: EnvironmentContext, ActionContext() {
    // todo: add fetch by GitHub repository URL
    var registry: String = "https://registry.archguard.org/"
    val pluginDirectory: String = "plugins"

    override fun getRuntimeEnvironmentVariables(): Map<String, String> {
        return mapOf()
    }
}