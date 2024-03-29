package org.archguard.core.internal

/**
 * Factory for creating instances of [org.archguard.codedb.automate.api.Task].
 */
interface Factory<T> {
    fun create(): T
}