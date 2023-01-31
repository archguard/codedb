package org.archguard.codedb.gitignore

import org.slf4j.LoggerFactory
import java.nio.file.FileSystem
import java.nio.file.FileSystems

interface IgnorePathMatcher {
    fun match(path: String): Boolean
}

class SimpleMatcherIgnore(val path: String) : IgnorePathMatcher {
    override fun match(path: String): Boolean {
        return this.path == path
    }
}

var fileSystem: FileSystem = FileSystems.getDefault()

class FilepathMatcherIgnore(val path: String) : IgnorePathMatcher {
    override fun match(path: String): Boolean {
        val absolute = fileSystem.getPath(path)
        logger.warn("Matching $absolute with ${this.path}")
        return fileSystem.getPathMatcher("glob:${this.path}").matches(absolute)
    }

    companion object {
        var logger = LoggerFactory.getLogger(FilepathMatcherIgnore::class.java)
        fun convertGlobToRegex(glob: String): String {
            val inDoubleQuotes = false
            val regex = StringBuilder("^")
            var escaping = false
            var inClass = false
            for (current in glob.toCharArray()) {
                when {
                    escaping -> {
                        regex.append(current)
                        escaping = false
                    }

                    current == '\\' -> {
                        escaping = true
                        if (inClass) regex.append("\\\\")
                    }

                    current == '*' -> regex.append(if (inClass) "*" else ".*")
                    current == '?' -> regex.append(if (inClass) "?" else ".")
                    current == '[' -> {
                        inClass = true
                        regex.append("[")
                    }

                    current == ']' -> {
                        inClass = false
                        regex.append("]")
                    }

                    current == '.' -> regex.append("\\.")
                    current == '/' -> regex.append("/")
                    current == '{' -> regex.append("(")
                    current == '}' -> regex.append(")")
                    current == ',' -> regex.append("|")
                    current == '"' -> if (inDoubleQuotes) regex.append("\\\"") else regex.append("\"")
                    else -> regex.append(current)
                }
            }
            return regex.append('$').toString()
        }
    }
}