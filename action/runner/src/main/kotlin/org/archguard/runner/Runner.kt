package org.archguard.runner

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option

fun main(args: Array<String>) = Runner().main(args)
class Runner : CliktCommand(help = "ArchGuard Action Runner") {
    // todo: add auto lookup for config file
    private val configFile: String by option(help = "Number of greetings").default("archguard.yml")

    val worker = Worker()

    override fun run() {
        logger.info("Runner started")
        logger.info("Current directory: ${System.getProperty("user.dir")}")
        logger.info("configFile: $configFile")

        echo("Hello, World!")
    }

    companion object {
        val logger = org.slf4j.LoggerFactory.getLogger(Runner::class.java)
    }
}
