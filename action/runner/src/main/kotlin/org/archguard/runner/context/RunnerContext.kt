package org.archguard.runner.context

import com.google.gson.stream.JsonToken

/**
 * Runner context
 */
class RunnerContext: EnvironmentContext, ActionContext() {
    override fun getRuntimeEnvironmentVariables(): Map<String, String> {
        return mapOf()
    }
}