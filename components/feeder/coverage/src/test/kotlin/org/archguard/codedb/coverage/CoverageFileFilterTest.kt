package org.archguard.codedb.coverage

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class CoverageFileFilterTest {

    @Test
    fun byRegex() {
        CoverageFileFilter().isMatch("clover.xml") shouldBe true
    }

    @Test
    fun testFinalize() {
        val filter = CoverageFileFilter()
        filter.isMatch("coverage.xml")
        filter.isMatch("jacoco.xml")
        filter.isMatch("hello.txt")

        filter.finalize()
    }
}