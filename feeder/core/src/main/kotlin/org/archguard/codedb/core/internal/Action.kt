package org.archguard.codedb.core.internal

interface Action<T> {
    fun execute(t: T)
}