package org.archguard.codedb

import java.nio.file.Path
import kotlin.io.path.createTempDirectory

class Workdir {
    fun dir(workdir: String): Path {
        val tempDir = createTempDirectory("archguard")
        return tempDir.resolve(workdir)
    }
}