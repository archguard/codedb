package org.archguard.codedb.coverage

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class SearcherTest {

    @Test
    fun byRegex() {
        Searcher().isMatch("coverage.xml") shouldBe true
    }
}