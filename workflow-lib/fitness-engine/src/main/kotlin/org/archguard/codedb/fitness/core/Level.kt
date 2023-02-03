package org.archguard.codedb.fitness.core

import java.io.Serializable

/**
 *  Sonar refs: https://docs.sonarqube.org/latest/user-guide/metric-definitions/
 */
enum class Level {
    A,
    B,
    C,
    D,
    E,
    F,
}

interface Metric<T : Serializable> {
    fun name(): String

    fun valueType(): Class<T>

    fun level(): Level
}
