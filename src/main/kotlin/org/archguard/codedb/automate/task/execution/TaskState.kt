package org.archguard.codedb.automate.task.execution

interface TaskState {
    fun getExecuted(): Boolean

    fun getFailure(): Throwable?

    fun rethrowFailure()

    fun getDidWork(): Boolean

    fun getSkipped(): Boolean

    fun getSkipMessage(): String?

    fun getUpToDate(): Boolean
}