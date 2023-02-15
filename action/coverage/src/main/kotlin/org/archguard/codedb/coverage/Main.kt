package org.archguard.codedb.coverage

import org.archguard.action.exec.CommandArgs

val logger = org.slf4j.LoggerFactory.getLogger("CoverageAction")

fun main(args: Array<String>) {
    // println args
    logger.info("args: ${args.joinToString(" ")}")
    val settings = CoverageSettings.from(CommandArgs.argsToMap(args))
}
