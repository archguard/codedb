package org.archguard.codedb.automate.task.core.factory

import org.archguard.codedb.automate.internal.Project
import org.archguard.codedb.automate.task.api.Task

class TaskIdentity<T : Task>(val type: Class<T>, val name: String? = null, val project: Project? = null) {

}