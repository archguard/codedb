package org.archguard.codedb.automate.task.core.factory

import org.archguard.codedb.automate.internal.Project
import org.archguard.codedb.automate.task.api.Task
import kotlin.reflect.KClass

class TaskIdentity<T : Task>(val type: KClass<T>, val name: String? = null, val project: Project? = null) {

}