package org.archguard.codedb.automate.task.core.factory

import org.archguard.codedb.automate.task.api.Task

interface TaskFactory {
    fun <S : Task> create(identity: TaskIdentity<S>, constructorArgs: Array<Any>): S?
}

