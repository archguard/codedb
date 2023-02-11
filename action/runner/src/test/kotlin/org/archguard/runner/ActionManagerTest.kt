package org.archguard.runner

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class ActionManagerTest {
    @Test
    @Disabled
    fun `test action manager`() {
        val actionManager = ActionManager()
        actionManager.executeDownload(DownloadInfo.from("https://registry.archguard.org/", "actions/setup@v1")!!, "plugins")

    }
}