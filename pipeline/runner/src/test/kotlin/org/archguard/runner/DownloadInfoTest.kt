package org.archguard.runner

import io.kotest.matchers.shouldBe
import org.archguard.runner.runner.DownloadInfo
import org.junit.jupiter.api.Test
import java.net.URL

class DownloadInfoTest {
    @Test
    fun `test download info`() {
        val downloadInfo = DownloadInfo.from("https://plugin.archguard.org/", "actions/setup@v1")!!

        downloadInfo.metaUrl shouldBe URL("https://plugin.archguard.org/actions/setup.json")
    }

    @Test
    fun `should log error when action name error`() {
        val downloadInfo = DownloadInfo.from("https://plugin.archguard.org/", "actions/setup@v1@v1")
        downloadInfo shouldBe null
    }

}