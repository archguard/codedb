package org.archguard.codedb.build.internal

/**
 * Factory for creating instances of [org.archguard.codedb.build.api.Task].
 */
interface Factory<T> {
    fun create(): T
}