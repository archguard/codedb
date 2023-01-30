package org.archguard.codedb.walkdir

import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.ints.shouldBeLessThan
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import java.io.File
import java.nio.file.Paths

class DirectoryWalkerTest {
    @Test
    fun testWalk() {
        // TODO: replace with absolute path
        val rootDir = Paths.get("").toAbsolutePath().parent.parent.parent.toString()
        var count = 0
        val channel = Channel<File>()
        runBlocking {
            launch {
                for (fileJob in channel) {
                    println("file: $fileJob")
                    count++
                }
            }

            val walker = DirectoryWalker(rootDir, channel, true)
            walker.start()

            channel.close()

            println(count)
        }

        count shouldBeGreaterThan 100

        // if with `build/` directory, the count should be greater than 5000
        count shouldBeLessThan 5000
    }
}