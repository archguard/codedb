package org.archguard.codedb.server

import org.archguard.codedb.server.fitness.KotlinReplWrapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ReplDemoTest {
    private lateinit var compiler: KotlinReplWrapper


    @BeforeEach
    internal fun setUp() {
        this.compiler = KotlinReplWrapper()
    }

    @Test
    internal fun simple_eval() {
        compiler.eval("val x = 3")
    }

}