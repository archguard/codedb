package org.archguard.action.checkout


val logger = org.slf4j.LoggerFactory.getLogger("CheckoutAction")

fun main(args: Array<String>) {
    logger.info("args: ${args.joinToString(" ")}")
    // args to GitSourceSettings
    val settings = GitSourceSettings.fromArgs(args)

    executeGitCheckout(settings)
}

