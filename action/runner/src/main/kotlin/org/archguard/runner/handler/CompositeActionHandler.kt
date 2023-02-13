package org.archguard.runner.handler

import org.archguard.runner.context.RunnerContext
import org.archguard.runner.pipeline.ActionName
import org.archguard.runner.pipeline.ActionStep

class CompositeActionHandler(
    val step: ActionStep,
    val context: RunnerContext
) : Handler {
    // exec plugin: `java -jar plugin.jar`
    override fun runSync() {
        val actionName = ActionName.from(step.uses) ?: throw Exception("Invalid action name: ${step.uses}")
        val pluginPath = "${context.pluginDirectory}/${actionName.filename("jar")}"
        execJavaJar(pluginPath)
    }

    private fun execJavaJar(jar: String) {
        val command = "java -jar $jar ${step.toCommandLine()}"
        println("exec: $command")

        val process = Runtime.getRuntime().exec(command)
        process.waitFor()
        println("exit code: ${process.exitValue()}")

        val output = process.inputStream.bufferedReader().readText()
        println(output)
    }
}