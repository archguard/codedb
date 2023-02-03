package org.archguard.codedb.coverage

import com.google.bazel.LcovParser.parse
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.io.File

class LcovParserTest {
    @Test
    @Disabled
    fun test() {
        val basepath = this.javaClass.classLoader.getResource("lcov.info")!!.path
        val inputStream = File(basepath).inputStream()

        val lcov = parse(inputStream)
        println(lcov)
    }
}
