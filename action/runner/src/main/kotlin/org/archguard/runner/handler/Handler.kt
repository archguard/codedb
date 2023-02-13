package org.archguard.runner.handler

interface Handler {
    fun runSync()

    fun prepareExecution() {}
}
