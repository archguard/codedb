package org.archguard.action.core

import org.slf4j.Logger
import org.slf4j.LoggerFactory

// todo:
class Tracing {
    fun info(str: String) {
        logger.info(str)
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(Tracing::class.java)
    }
}
