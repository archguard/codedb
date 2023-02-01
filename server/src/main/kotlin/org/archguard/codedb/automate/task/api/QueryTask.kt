package org.archguard.codedb.automate.task.api

import org.archguard.codedb.core.Project

abstract class AbstractQueryTask() : Task {
    open lateinit var project: Project
}