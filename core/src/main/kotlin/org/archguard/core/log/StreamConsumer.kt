package org.archguard.core.log

/**
 * A consumer of a stream of lines.
 */
interface StreamConsumer {
    fun consumeLine(line: String)

    fun stop() {}
}
