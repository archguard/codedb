package org.archguard.codedb.build.task.factory

import org.archguard.codedb.build.task.api.Task

interface TaskFactory {
    fun <S : Task> create(taskIdentity: TaskIdentity<S>, constructorArgs: Array<Any>): S
}