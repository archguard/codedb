package org.archguard.codedb.coverage.clover

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.io.File

class OpenCloverServiceTest {

    @Test
    fun testParseFileFromResource() {
        // get file from resources, use file name
        val path = this.javaClass.classLoader.getResource("openclover/clover.xml")!!.path

        val dataPoint = OpenCloverService().parse(File(path))
        assertNotNull(dataPoint)
    }
}