package org.archguard.codedb.gitignore.ignore

import io.kotest.matchers.shouldBe
import org.archguard.codedb.gitignore.Gitignore
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import java.nio.file.Paths

internal class GitignoreTest {
    @Test
    fun testNewGitIgnore() {
        val basepath = this.javaClass.classLoader.getResource("ignore/should_include")!!.path
        val path = this.javaClass.classLoader.getResource("ignore/.ignoretest")!!.path

        val ignore = Gitignore.create(path)!!
        assertFalse(ignore.match(basepath, false))
    }

    @Test
    fun testProjectGitignore() {
        val rootDir = Paths.get("").toAbsolutePath()

        val ignore = Gitignore.create(rootDir.resolve(".gitignore").toString())!!

        assertFalse(ignore.match(rootDir.resolve("build").toString(), false))
        assertFalse(ignore.match(rootDir.resolve("analyser_estimate").toString(), false))
    }

    @Test
    fun testByLines() {
        val ignore = Gitignore.fromLines("", listOf("*.kt"))

        ignore.matchText("test.kt", false) shouldBe true
        ignore.matchText("test.kt", true) shouldBe true
        ignore.matchText("test.java", true) shouldBe false
    }
}