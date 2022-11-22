package org.archguard.codedb.automate.internal

interface Action<T> {
    fun execute(t: T)
}