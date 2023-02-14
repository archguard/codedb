package org.archguard.action.exec

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File

/**
 * Executes a command and returns the output.
 */
class Command {
    fun execJar(args: List<String>, workdir: String, options: ExecOptions = ExecOptions(cwd = workdir)): Int {
        val processBuilder = ProcessBuilder("java", "-jar", *args.toTypedArray())

        return doExecute(processBuilder, options)
    }

    fun exec(commandLine: String, args: List<String>, options: ExecOptions): Int {
        if (!options.silent) {
            logger.info("Executing: $commandLine ${args.joinToString(" ")} in ${options.cwd}")
        }

        val processBuilder = ProcessBuilder(commandLine, *args.toTypedArray())

        return doExecute(processBuilder, options)
    }

    private fun doExecute(processBuilder: ProcessBuilder, options: ExecOptions): Int {
        val process = processBuilder
            .directory(File(options.cwd))
            .start()

        val exitCode = process.waitFor()

        process.inputStream.bufferedReader().use { it ->
            it.forEachLine { line ->
                options.listeners.stdout(line)
            }
        }

        if (exitCode != 0 && !options.ignoreReturnCode) {
            process.errorStream.bufferedReader().use {
                it.forEachLine { line ->
                    options.listeners.stderr(line)
                }
            }
        }

        return exitCode
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(Command::class.java)
    }
}

class ExecOptions(
    val cwd: String = "",
    val env: MutableMap<String, String> = mutableMapOf(),
    val silent: Boolean = false,
    val ignoreReturnCode: Boolean = false,
    val listeners: ExecListeners = object : ExecListeners {}
) {

}


interface ExecListeners {
    fun stdout(data: String) {}
    fun stderr(data: String) {}
    fun stdline(data: String) {}
    fun errline(data: String) {}
    fun debug(data: String) {}
}
