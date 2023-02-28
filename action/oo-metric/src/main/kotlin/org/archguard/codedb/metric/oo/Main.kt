package org.archguard.codedb.metric.oo

import chapi.domain.core.CodeDataStruct
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.cast
import org.jetbrains.kotlinx.dataframe.io.read

val logger = org.slf4j.LoggerFactory.getLogger("OoMetric")
/**
 * OO metric for Java/Kotlin, etc.
 *
 * input args: CodeDataStructure
 */
fun main(args: Array<String>) {
    logger.info("args: ${args.joinToString(" ")}")
    val settings = OoMetricSetting.fromArgs(args)

    val structDataFrame = DataFrame.read(settings.input).cast<CodeDataStruct>()
    metric(structDataFrame)
}
