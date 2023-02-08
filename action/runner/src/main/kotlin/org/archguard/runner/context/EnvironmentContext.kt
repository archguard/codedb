package org.archguard.runner.context

/**
 * A key value pair of environment variables
 */
interface EnvironmentContext {
    // C#: IEnumerable<KeyValuePair<string, string>> GetRuntimeEnvironmentVariables();
    // Java: Map<String, String> getRuntimeEnvironmentVariables();
    // Kotlin: Map<String, String>
    fun getRuntimeEnvironmentVariables(): Map<String, String>
}
