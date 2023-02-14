package org.archguard.runner.handler

import org.archguard.action.exec.Command
import org.archguard.action.exec.ExecListeners
import org.archguard.action.exec.ExecOptions
import org.archguard.runner.context.RunnerContext
import org.archguard.runner.pipeline.ActionStep
import org.archguard.runner.pipeline.UsesAction
import org.slf4j.Logger
import org.slf4j.LoggerFactory.*
import java.io.File

class CompositeActionHandler(
    private val step: ActionStep,
    val context: RunnerContext,
    val name: String
) : Handler {
    override fun getDisplayName(): String = name

    override fun runSync() {
        logger.info("Running composite action: ${step.uses}, with args: ${step.toCommandList(context.actionEnv)}")
        val usesAction = UsesAction.from(step.uses) ?: throw Exception("Invalid action name: ${step.uses}")
        val pluginPath = "${context.pluginDirectory}/${usesAction.filename("jar")}"
        execJavaJar(File(pluginPath).absolutePath)
    }

    private fun execJavaJar(jar: String) {
        val args: List<String> = listOf(jar) + step.toCommandList(context.actionEnv)
        Command().execJar(args, context.pluginDirectory, ExecOptions(
            silent = true,
            cwd = context.pluginDirectory,
            listeners = object: ExecListeners {
                override fun stdout(line: String) {
                    logger.info(line)
                }

                override fun stderr(line: String) {
                    logger.error(line)
                }
            }))
    }

    companion object {
        val logger: Logger = getLogger(CompositeActionHandler::class.java)
    }
}
