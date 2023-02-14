package org.archguard.runner.context

import java.io.File

/**
 * todo: to support action and worker run in different host
 */
open class HostContext(val hostType: String) {
    protected val logFile: File by lazy { File(directory, "log.txt") }
    protected val directory: String by lazy {
        System.getProperty("user.dir")
        // todo: create temp dir
    }
}
