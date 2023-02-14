package org.archguard.runner.handler

import org.archguard.action.exec.Command
import org.archguard.runner.context.RunnerContext
import org.archguard.runner.pipeline.ActionStep
import org.archguard.runner.pipeline.UsesAction
import java.io.File

class CompositeActionHandler(
    private val step: ActionStep,
    val context: RunnerContext
) : Handler {
    override fun runSync() {
        val usesAction = UsesAction.from(step.uses) ?: throw Exception("Invalid action name: ${step.uses}")
        val pluginPath = "${context.pluginDirectory}/${usesAction.filename("jar")}"
        execJavaJar(File(pluginPath).absolutePath)
    }

    // exec: `java -jar plugin.jar`
    private fun execJavaJar(jar: String) {
        val args: List<String> = listOf("java", "-jar", jar) + step.toCommandList(context.actionEnv)
        Command().run(args, File(context.pluginDirectory))
    }
}
