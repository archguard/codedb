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

    fun exec(commandLine: String, args: List<String> = emptyList(), options: ExecOptions): Int {
        if (!options.silent) {
            logger.info("Executing: $commandLine ${args.joinToString(" ")}")
        }

        val commandWithArgs = mutableListOf("bash", "-c", "$commandLine ${args.joinToString(" ")}")
        val processBuilder = ProcessBuilder(*commandWithArgs.toTypedArray())

        return doExecute(processBuilder, options)
    }

    private fun doExecute(processBuilder: ProcessBuilder, options: ExecOptions): Int {
        val process = processBuilder.directory(File(options.cwd)).start()
        val exitCode = process.waitFor()

        process.inputStream.bufferedReader().forEachLine(options.listeners::stdout)

        if (exitCode != 0 && !options.ignoreReturnCode) {
            process.errorStream.bufferedReader().forEachLine(options.listeners::stderr)
        }

        return exitCode
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(Command::class.java)

        fun exec(commandLine: String, args: List<String> = emptyList(), options: ExecOptions = ExecOptions()): Int {
            return Command().exec(commandLine, args, options)
        }
    }
}

class ExecOptions(
    val cwd: String = "",
    val env: MutableMap<String, String> = mutableMapOf(),
    val silent: Boolean = false,
    val ignoreReturnCode: Boolean = false,
    val listeners: ExecListeners = object : ExecListeners {}
)

