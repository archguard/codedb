package org.archguard.runner

import io.kotest.matchers.shouldBe
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.archguard.runner.pipeline.Scalar
import org.junit.jupiter.api.Test
import java.io.File

class ActionManifestManagerTest {
    @Test
    fun `should load action manifest`() {
        val source = this.javaClass.classLoader.getResource("pipeline/basic.yml")!!.path
        val actionManifest = ActionManifestManager().load(File(source).readText())

        actionManifest.name shouldBe "ArchGuard 3.0 - CodeDB"
        actionManifest.config.server.url shouldBe "http://localhost:8084"

        val jobs = actionManifest.jobs
        jobs.size shouldBe 2
    }

    @Test
    fun `should handle for job`() {
        val source = this.javaClass.classLoader.getResource("pipeline/basic.yml")!!.path
        val actionManifest = ActionManifestManager().load(File(source).readText())

        val jobs = actionManifest.jobs

        val firstJob = jobs["backend"]!!
        firstJob.config.displayName shouldBe "Backend"
        firstJob.config.languages shouldBe listOf("java", "kotlin")

        firstJob.steps.size shouldBe 4
        firstJob.steps[0].uses shouldBe "actions/checkout@v3"
        firstJob.steps[1].uses shouldBe "analyser/source-code@v1"

        firstJob.steps[1].with["features"] shouldBe Scalar.Array(listOf(Scalar.String("datamap")))
    }
}