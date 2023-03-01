package org.archguard.runner

import io.kotest.matchers.shouldBe
import org.archguard.runner.runner.ActionManifestManager
import org.archguard.runner.serial.Scalar
import org.junit.jupiter.api.Test
import java.io.File

class ActionManifestManagerTest {
    @Test
    fun `should load action manifest`() {
        val source = this.javaClass.classLoader.getResource("pipeline/basic.yml")!!.path
        val actionManifest = ActionManifestManager().load(File(source).readText())

        actionManifest.name shouldBe "ArchGuard 3.0 - CodeDB"
        actionManifest.env.server.url shouldBe "http://localhost:8084"

        val jobs = actionManifest.jobs
        jobs.size shouldBe 2

        val sourceCodeAction = jobs["backend"]!!.steps[1]
        sourceCodeAction.with["server-url"] shouldBe Scalar.String("http://localhost:8084")
    }

    @Test
    fun `should handle for job`() {
        val source = this.javaClass.classLoader.getResource("pipeline/basic.yml")!!.path
        val actionManifest = ActionManifestManager().load(File(source).readText())

        val jobs = actionManifest.jobs

        val firstJob = jobs["backend"]!!
        firstJob.config.displayName shouldBe "Backend"

        firstJob.steps.size shouldBe 4
        firstJob.steps[0].uses shouldBe "actions/checkout@v3"
        firstJob.steps[1].uses shouldBe "analyser/source-code@v1"

        firstJob.steps[1].with["features"] shouldBe Scalar.List(listOf(Scalar.String("datamap")))
    }
}
