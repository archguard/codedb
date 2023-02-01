package org.archguard.codedb.fitness.piece

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