package org.archguard.codedb.build.task.api

import org.archguard.codedb.build.internal.Project

abstract class AbstractQueryTask() : Task {
    open lateinit var project: Project
}