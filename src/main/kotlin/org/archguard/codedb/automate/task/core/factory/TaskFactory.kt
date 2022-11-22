package org.archguard.codedb.automate.task.core.factory

import org.archguard.codedb.automate.task.api.Task

interface TaskFactory {
    fun <S : Task> create(taskIdentity: TaskIdentity<S>, constructorArgs: Array<Any>): S
}