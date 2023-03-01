package org.archguard.runner.handler

interface Handler {
    fun getDisplayName(): String

    fun runSync()

    fun prepareExecution() {}
}
