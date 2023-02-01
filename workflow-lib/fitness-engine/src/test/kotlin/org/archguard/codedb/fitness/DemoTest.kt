package org.archguard.codedb.fitness

import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.shouldBe
import org.archguard.codedb.fitness.document.DocumentCode
import org.junit.jupiter.api.Test

class DemoTest {
    @Test
    fun test() {
        val documentCode = object : DocumentCode() {
            override fun hasReadmeFile(): Boolean {
                return true
            }

            override fun hasDecisionRecord(): Boolean {
                return true
            }
        }

        documentCode.hasReadmeFile() shouldBe true
    }
}
