package org.archguard.codedb.fitness.piece

/**
 * quality API
 */
abstract class Quality {
    /**
     * @return the quality of the code base
     */
    abstract fun testCoverage(): Double

    /**
     * get from SonarQube or others..
     */
    abstract fun maintainability(): Double
}

