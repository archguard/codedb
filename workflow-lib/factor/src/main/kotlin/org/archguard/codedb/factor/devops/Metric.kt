package org.archguard.codedb.factor.devops

import kotlinx.serialization.Serializable

enum class LEVEL {
    ELITE, HIGH, MEDIUM, LOW, INVALID
}

@Serializable
open class Metric(
    val value: Double = 0.0,
    val level: LEVEL = LEVEL.INVALID,
    val startTimestamp: Long = 0,
    val endTimestamp: Long = 0
)

