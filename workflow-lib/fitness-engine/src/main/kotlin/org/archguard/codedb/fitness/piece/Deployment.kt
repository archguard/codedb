package org.archguard.codedb.fitness.piece

abstract class Deployment {
    /**
     * deployment failure rate
     */
    abstract fun errorRate(): Double
}
