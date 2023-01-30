package org.archguard.codedb.coverage

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class CoverageFileFilterTest {

    @Test
    fun byRegex() {
        CoverageFileFilter().isMatch("coverage.xml") shouldBe true
    }
}