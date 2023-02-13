package org.archguard.runner.handler

import org.archguard.runner.context.RunnerContext
import org.archguard.runner.pipeline.ActionName
import org.archguard.runner.pipeline.ActionStep
import java.io.File

class CompositeActionHandler(
    val step: ActionStep,
    val context: RunnerContext
) : Handler {
    // exec plugin: `java -jar plugin.jar`
    override fun runSync() {
        val actionName = ActionName.from(step.uses) ?: throw Exception("Invalid action name: ${step.uses}")
        val pluginPath = "${context.pluginDirectory}/${actionName.filename("jar")}"
        execJavaJar(File(pluginPath).absolutePath)
    }

    private fun execJavaJar(jar: String) {
        val args: List<String> = listOf("java", "-jar", jar) + step.toCommandList()
        val processBuilder = ProcessBuilder(args)

        val process = processBuilder
            .directory(File(context.pluginDirectory))
            .inheritIO()
            .start()

        val text = process.inputStream.bufferedReader().readText()
        println("text: $text")

        val exitCode = process.waitFor()
        if (exitCode != 0) {
            throw Exception("Plugin execution failed with exit code $exitCode")
        }
    }


}