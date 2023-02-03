package org.archguard.codedb.fitness.slice.deployment

abstract class Deployment {
    /**
     * deployment failure rate
     */
    abstract fun errorRate(): Double
}
