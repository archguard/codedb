package org.archguard.codedb.automate.task.core.factory

import org.archguard.codedb.automate.task.DefaultTask
import org.archguard.codedb.automate.task.api.Task

class DefaultTaskFactory : TaskFactory {
    override fun <S : Task> create(identity: TaskIdentity<S>, constructorArgs: Array<Any>): S {
        // todo: create task in here
        val instance: Task = DefaultTask();
        return identity.type.cast(instance)
    }
}