package org.archguard.runner

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.net.URL

class DownloadInfoTest {
    @Test
    fun `test download info`() {
        val downloadInfo = DownloadInfo.from("https://plugin.archguard.org/", "actions/setup@v1")!!

        downloadInfo.jarUrl shouldBe URL("https://plugin.archguard.org/actions/setup/v1/setup-v1.jar")
        downloadInfo.sha256Url shouldBe URL("https://plugin.archguard.org/actions/setup/v1/setup-v1.sha256")
    }

    @Test
    fun `should log error when action name error`() {
        val downloadInfo = DownloadInfo.from("https://plugin.archguard.org/", "actions/setup@v1@v1")
        downloadInfo shouldBe null
    }
}