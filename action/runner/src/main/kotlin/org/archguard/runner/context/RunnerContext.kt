package org.archguard.runner.context

import org.archguard.runner.pipeline.ActionEnv
import java.io.File

const val DEFAULT_MANIFEST_PATH: String = "archflow.yml"

/**
 * Runner context
 */
open class RunnerContext: EnvironmentContext, ActionContext() {
    var registry: String = "https://registry.archguard.org/"

    val pluginDirectory: String = "plugins"

    var manifestYmlPath: String = DEFAULT_MANIFEST_PATH

    var workingDirectory: File = File(".")

    // share in context
    val actionEnv: ActionEnv = ActionEnv()

    override fun getRuntimeEnvironmentVariables(): Map<String, String> {
        return mapOf()
    }
}