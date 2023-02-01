package org.archguard.codedb.fitness.quality

/**
 * quality API
 */
abstract class QualityCode {
    /**
     * @return the quality of the code base
     */
    abstract fun testCoverage(): Double

    /**
     * get from SonarQube or others..
     */
    abstract fun maintainability(): Double
}

