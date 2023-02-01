package org.archguard.codedb.fitness.deployment

abstract class Deployment {
    /**
     * deployment failure rate
     */
    abstract fun errorRate(): Double
}
