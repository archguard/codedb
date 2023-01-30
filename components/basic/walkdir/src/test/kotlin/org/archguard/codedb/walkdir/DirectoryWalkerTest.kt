package org.archguard.codedb.walkdir

import io.kotest.matchers.ints.shouldBeGreaterThan
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import java.io.File
import java.nio.file.Paths

class DirectoryWalkerTest {
    @Test
    fun testWalk() {
        val rootDir = Paths.get("").toAbsolutePath().parent.parent.toString()
        var count = 0
        val channel = Channel<File>()
        runBlocking {
            launch {
                for (fileJob in channel) {
                    println("file: $fileJob")
                    count++
                }
            }

            val walker = DirectoryWalker(rootDir, channel)
            walker.start()

            channel.close()

            println(count)
        }

        count shouldBeGreaterThan 10
    }
}