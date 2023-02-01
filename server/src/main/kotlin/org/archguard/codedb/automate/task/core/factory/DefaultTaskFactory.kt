package org.archguard.codedb.automate.task.core.factory

import org.archguard.codedb.automate.task.api.Task
import org.archguard.codedb.automate.task.reflect.DirectInstantiator
import kotlin.reflect.cast

class DefaultTaskFactory : TaskFactory {
    private val instantiator = DirectInstantiator()

    override fun <S : Task> create(identity: TaskIdentity<S>, constructorArgs: Array<Any>): S {
        val instance = instantiator.newInstanceByTypename(identity.type, *constructorArgs)
        return identity.type.cast(instance)
    }

    // todo: add process to get classInfo
}