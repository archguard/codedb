package org.archguard.codedb.fitness.service

/**
 * include web service
 */
abstract class WebService {
    /**
     * like SkyWalking, NewRelic or others.
     */
    fun hasMetrics(): Boolean {
        return false
    }
}
