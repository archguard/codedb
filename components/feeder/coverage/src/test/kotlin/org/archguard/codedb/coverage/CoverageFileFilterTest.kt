package org.archguard.codedb.coverage

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class CoverageFileFilterTest {

    @Test
    @Disabled
    fun byRegex() {
        CoverageFileFilter().isMatch("clover.xml") shouldBe true
    }

    @Test
    @Disabled
    fun testFinalize() {
        val filter = CoverageFileFilter()
        filter.isMatch("coverage.xml") shouldBe false
        filter.isMatch("jacoco2.xml") shouldBe false
        filter.isMatch("hello.txt") shouldBe false

        filter.finalize()
    }
}
