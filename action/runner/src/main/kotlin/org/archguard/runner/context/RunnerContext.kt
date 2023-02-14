package org.archguard.runner.context

import org.archguard.runner.pipeline.ActionConfig
import java.io.File

const val DEFAULT_MANIFEST_PATH: String = "archflow.yml"

/**
 * Runner context
 */
open class RunnerContext: EnvironmentContext, ActionContext() {
    // todo: add fetch by GitHub repository URL
    var registry: String = "https://registry.archguard.org/"
    val pluginDirectory: String = "plugins"
    var manifestYmlPath: String = DEFAULT_MANIFEST_PATH
    var workingDirectory: File = File(".")

    // share in context
    val actionConfig: ActionConfig = ActionConfig()

    override fun getRuntimeEnvironmentVariables(): Map<String, String> {
        return mapOf()
    }
}