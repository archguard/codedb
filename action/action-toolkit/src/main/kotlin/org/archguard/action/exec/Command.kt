package org.archguard.action.exec

import java.io.File

/**
 * Executes a command and returns the output.
 */
class Command {
    fun run(args: List<String>, workdir: File): String {
        val processBuilder = ProcessBuilder(args)

        val process = processBuilder
            .directory(workdir)
            .inheritIO()
            .start()

        val text = process.inputStream.bufferedReader().readText()

        val exitCode = process.waitFor()
        if (exitCode != 0) {
            throw Exception("Execution failed with exit code $exitCode, command: $args")
        }

        return text
    }
}
