package org.archguard.runner

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import org.archguard.runner.context.DEFAULT_MANIFEST_PATH
import org.slf4j.Logger
import java.io.File
import kotlin.system.exitProcess
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) = Runner().main(args)
class Runner : CliktCommand(help = "ArchGuard Action Runner") {
    // todo: add auto lookup for config file
    private val runnerConfig: String by option(help = "Number of greetings").default(DEFAULT_MANIFEST_PATH)

    private val worker = Worker()

    override fun run() {
        logger.info("Runner started")
        logger.info("Current directory: ${System.getProperty("user.dir")}")
        logger.info("configFile: $runnerConfig")

        if(!File(runnerConfig).exists()) {
            logger.error("Config file not found: $runnerConfig")
            exitProcess(1)
        }

        measureTimeMillis {
            worker.run(runnerConfig)
        }.let {
            logger.info("Runner finished in ${it}ms = ${it / 1000.0}s")
        }
    }

    companion object {
        val logger: Logger = org.slf4j.LoggerFactory.getLogger(Runner::class.java)
    }
}
