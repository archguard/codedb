package org.archguard.codedb.fitness

import io.kotest.matchers.shouldBe
import org.archguard.codedb.fitness.slice.document.DocumentCode
import org.junit.jupiter.api.Test

class FitnessDemoTest {
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
