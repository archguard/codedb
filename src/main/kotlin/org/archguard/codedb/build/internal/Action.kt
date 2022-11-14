package org.archguard.codedb.build.internal

interface Action<T> {
    fun execute(t: T)
}