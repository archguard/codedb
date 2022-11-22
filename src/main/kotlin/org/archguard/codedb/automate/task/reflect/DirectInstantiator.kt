package org.archguard.codedb.automate.task.reflect

import org.archguard.codedb.automate.task.api.Task
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

class DirectInstantiator {
    fun <T: Task> newInstance(type: KClass<out T>, vararg parameters: Any?): T? {
        return doCreate(type, null, parameters)
    }

    private fun <T: Task> doCreate(type: KClass<out T>, nothing: Nothing?, parameters: Array<out Any?>): T? {
        if (parameters.isNotEmpty() && type.typeParameters.isNotEmpty()) {
            return type.primaryConstructor?.call(*parameters)
        } else {
            return type.primaryConstructor?.call()
        }
    }

}
