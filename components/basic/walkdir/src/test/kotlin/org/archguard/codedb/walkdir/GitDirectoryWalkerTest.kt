package org.archguard.codedb.walkdir

import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.ints.shouldBeLessThan
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import java.io.File
import java.nio.file.Paths

class GitDirectoryWalkerTest {
    @Test
    fun testWalk() {
        // TODO: replace with absolute path
        val rootDir = Paths.get("").toAbsolutePath().parent.parent.parent.toString()
        var count = 0
        val channel = Channel<File>()
        runBlocking {
            launch {
                for (fileJob in channel) {
                    count++
                }
            }

            val walker = GitDirectoryWalker(rootDir, channel)
            walker.start()

            channel.close()
        }

        count shouldBeGreaterThan 100

        // if with `build/` directory, the count should be greater than 5000
        count shouldBeLessThan 5000
    }
}