package org.archguard.runner

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.io.File

class ActionManifestManagerTest {
    @Test
    fun `should load action manifest`() {
        val source = this.javaClass.classLoader.getResource("pipeline/basic.yml")!!.path
        val actionManifest = ActionManifestManager().load(File(source).readText())

        actionManifest.name shouldBe "ArchGuard 3.0 - CodeDB"
        actionManifest.config.server.url shouldBe "http://localhost:8084"
    }
}
