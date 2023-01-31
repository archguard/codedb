package org.archguard.codedb.coverage.clover

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.io.File

class OpenCloverServiceTest {

    @Test
    @Disabled
    fun testParseFileFromResource() {
        // get file from resources, use file name
        val path = this.javaClass.classLoader.getResource("openclover/clover.xml")!!.path

        val dataPoint = OpenCloverService().parse(File(path))
        assertNotNull(dataPoint)
    }
}