package org.archguard.codedb.repl

import io.kotest.matchers.shouldBe
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

    @Test
    internal fun local_file() {
        compiler.eval(
            """%use archguard

            var layer = layered {
                prefixId("org.archguard")
                component("controller") dependentOn component("service")
                组件("service") 依赖于 组件("repository")
            }
            """
        )

        val res = compiler.eval("layer.components().size")
        res.resultValue shouldBe 3

        val name = compiler.eval("layer.components()[0].name")
        name.resultValue shouldBe "controller"
    }
}