package org.archguard.runner.context

/**
 * A key value pair of environment variables
 */
interface EnvironmentContext {
    fun getRuntimeEnvironmentVariables(): Map<String, String>
}
