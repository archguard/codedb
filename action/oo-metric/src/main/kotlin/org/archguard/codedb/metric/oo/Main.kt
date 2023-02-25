package org.archguard.codedb.metric.oo

import kotlinx.serialization.Serializable


val logger = org.slf4j.LoggerFactory.getLogger("OoMetric")
/**
 * OO metric for Java/Kotlin, etc.
 *
 * input args: CodeDataStructure
 */
fun main(args: Array<String>) {
    logger.info("args: ${args.joinToString(" ")}")
    val settings = OoMetricSetting.fromArgs(args)
}

