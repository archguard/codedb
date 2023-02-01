package org.archguard.codedb.walkdir

import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.ints.shouldBeLessThan
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File
import java.nio.file.Paths

class GitDirectoryWalkerTest {
    private lateinit var walker: GitDirectoryWalker

    @BeforeEach
    fun setUp() {
        val rootDir = Paths.get("").toAbsolutePath().parent.parent.parent.toString()
        walker = GitDirectoryWalker(rootDir)
    }

    @Test
    fun testWalkWithChannel() {
        // TODO: replace with absolute path
        var count = 0
        val channel = Channel<File>()
        runBlocking {
            launch {
                for (fileJob in channel) {
                    count++
                }
            }

            walker.start(channel)
            channel.close()
        }

        count shouldBeGreaterThan 100

        // if with `build/` directory, the count should be greater than 5000
        count shouldBeLessThan 5000
    }

    @Test
    fun testWalkWithList() {
        val files = walker.start()
        files.size shouldBeGreaterThan 100
        files.size shouldBeLessThan 5000
    }
}