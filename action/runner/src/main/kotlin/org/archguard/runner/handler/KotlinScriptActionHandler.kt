package org.archguard.runner.handler

import org.archguard.codedb.repl.KotlinReplWrapper
import org.archguard.runner.context.RunnerContext
import org.archguard.runner.pipeline.ActionStep
import java.io.File

class KotlinScriptActionHandler(private val step: ActionStep, val context: RunnerContext) : Handler {
    override fun runSync() {
        val file = File(step.run)
        if (!file.exists()) {
            throw Exception("File not found: ${file.absolutePath}")
        }

        val readText = file.readText()
        KotlinReplWrapper().eval(readText)
    }
}
