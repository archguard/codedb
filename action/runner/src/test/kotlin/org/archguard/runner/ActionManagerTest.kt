package org.archguard.runner

import org.archguard.runner.runner.ActionManager
import org.archguard.runner.runner.DownloadInfo
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.io.File

class ActionManagerTest {
    @Test
    @Disabled
    fun `test action manager`() {
        val actionManager = ActionManager()

        val targetDir = "plugins"
        val pluginFile = File(targetDir)
        if (!pluginFile.exists()) {
            pluginFile.mkdirs()
        }

        val downloadInfo = DownloadInfo.from("https://registry.archguard.org/", "actions/checkout@0.1.0-SNAPSHOT")!!
        actionManager.executeDownload(downloadInfo, targetDir)
    }
}