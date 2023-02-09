package org.archguard.core.internal

interface Action<T> {
    fun execute(t: T)
}