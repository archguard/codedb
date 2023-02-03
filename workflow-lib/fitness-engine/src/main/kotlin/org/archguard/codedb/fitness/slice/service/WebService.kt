package org.archguard.codedb.fitness.slice.service

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

    /**
     * use some framework, like Prometheus
     */
    fun hasAlerts(): Boolean {
        return false
    }
}
