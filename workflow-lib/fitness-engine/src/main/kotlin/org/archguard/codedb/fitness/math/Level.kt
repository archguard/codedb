package org.archguard.codedb.fitness.math

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

interface Metric {
    fun getLevel(): Level
}
