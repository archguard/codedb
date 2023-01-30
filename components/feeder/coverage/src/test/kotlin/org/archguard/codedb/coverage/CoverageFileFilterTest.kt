package org.archguard.codedb.coverage

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class CoverageFileFilterTest {

    @Test
    fun byRegex() {
        CoverageFileFilter().isMatch("coverage.xml") shouldBe true
    }

    @Test
    fun testFinalize() {
        val filter = CoverageFileFilter()
        filter.check("coverage.xml")
        filter.check("jacoco.xml")
        filter.check("hello.txt")
        filter.finalize()
    }
}