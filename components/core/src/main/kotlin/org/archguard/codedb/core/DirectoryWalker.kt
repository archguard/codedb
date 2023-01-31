package org.archguard.codedb.core

import java.io.File
import kotlinx.coroutines.channels.Channel

abstract class DirectoryWalker(workdir: String) {
    protected val root = File(workdir)

    abstract fun start(): List<File>
    abstract suspend fun start(output: Channel<File>)
}
