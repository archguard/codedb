package org.archguard.codedb.core

import java.nio.file.FileSystem
import java.nio.file.FileSystems


abstract class BasicWalkerFilter() {
    private val fileSystem: FileSystem = FileSystems.getDefault()

    open fun isMatch(rules: List<String>, filename: String) = rules.any { isMatch(it, filename) }

    /**
     * default to blob match
     */
    open fun isMatch(filename: String, pattern: String): Boolean {
        return fileSystem.getPathMatcher("glob:$pattern").matches(fileSystem.getPath(filename))
    }

    abstract fun finalize()
}
