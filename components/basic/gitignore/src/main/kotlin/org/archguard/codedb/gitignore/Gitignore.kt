package org.archguard.codedb.gitignore

import org.jetbrains.annotations.TestOnly
import java.io.File

interface IgnoreMatcher {
    fun match(path: String, isDir: Boolean): Boolean
}

class Gitignore(val path: String) : IgnoreMatcher {
    private val ignorePatterns: ScanStrategy = IndexScanPatterns()
    private val acceptPatterns: ScanStrategy = IndexScanPatterns()

    override fun match(path: String, isDir: Boolean): Boolean {
        val relativePath = File(path).relativeTo(File(this.path)).path
        if (acceptPatterns.match(relativePath, isDir)) {
            return false
        }

        return ignorePatterns.match(relativePath, isDir)
    }

    // todo: refactor in here
    @TestOnly
    fun matchText(relativePath: String, isDir: Boolean): Boolean {
        if (acceptPatterns.match(relativePath, isDir)) {
            return false
        }

        return ignorePatterns.match(relativePath, isDir)
    }


    companion object {
        fun create(gitignore: String, vararg base: String): Gitignore? {
            val path: String = if (base.isNotEmpty()) {
                base[0]
            } else {
                File(gitignore).parent
            }

            val ignoreFile = File(gitignore)

            if (!ignoreFile.exists()) {
                println("Ignore file $gitignore does not exist")
                return null
            }

            return fromLines(path, ignoreFile.readLines())
        }

        fun fromLines(path: String, lines: List<String>): Gitignore {
            val g = Gitignore(path)
            lines
                .map { it.trim() }
                .filter { it.isNotEmpty() && !isComment(it) }
                .forEach {
                    if (it.startsWith("!")) {
                        g.acceptPatterns.add(it.substring(1))
                    } else {
                        g.ignorePatterns.add(it)
                    }
                }

            return g
        }

        private fun isComment(it: String) = it.startsWith("#")
    }
}