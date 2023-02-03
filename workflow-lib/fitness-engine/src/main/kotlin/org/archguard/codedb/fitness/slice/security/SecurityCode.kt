package org.archguard.codedb.fitness.slice.security

abstract class SecurityCode: Security {
    /**
     * security code
     */
    abstract fun owaspTopTenNumber(): Int

    abstract fun hasSecretsInCodeBase(): Boolean
}
