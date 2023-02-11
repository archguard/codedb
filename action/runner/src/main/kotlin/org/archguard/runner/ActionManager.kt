package org.archguard.runner

import org.archguard.runner.context.RunnerContext
import org.archguard.runner.pipeline.ActionExecutionData
import org.jetbrains.annotations.TestOnly
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File
import java.net.URL

class ActionManager : RunnerService() {
    /**
     * Prepare actions for execution, this will download the action and prepare the environment.
     */
    fun prepareActionsAsync(context: RunnerContext, actionData: ActionExecutionData) {
        // 1. create download information
        val downloadInfos: List<DownloadInfo> =
            actionData.steps.mapNotNull { DownloadInfo.from(context.registry, it.name) }

        // 2. prepare plugins dir
        val pluginsDir = File(context.pluginDirectory)
        if (!pluginsDir.exists()) {
            pluginsDir.mkdirs()
        }
        logger.info("Plugins directory: ${pluginsDir.absolutePath}")


        downloadInfos.forEach { downloadInfo ->
            // 2. download action
            downloadAction(context, downloadInfo)
        }
    }

    private fun downloadAction(context: RunnerContext, downloadInfo: DownloadInfo) {
        val targetDir = context.pluginDirectory

        executeDownload(downloadInfo, targetDir)
    }

    @TestOnly
    fun executeDownload(downloadInfo: DownloadInfo, targetDir: String) {
        val jarFile = downloadFile(downloadInfo.jarUrl, fiename(targetDir, downloadInfo, ".jar"))
        // todo: add verify for sha256
//        val shaFile = downloadFile(downloadInfo.sha256Url, fileOutput(targetDir, downloadInfo, ".sha256"))
    }

    /**
     * for examples: /tmp/plugins/checkout-0.1.0-SNAPSHOT.jar
     */
    private fun fiename(targetDir: String, downloadInfo: DownloadInfo, extName: String) =
        "$targetDir${File.separator}${downloadInfo.name}-${downloadInfo.version}$extName"

    private fun downloadFile(url: URL, target: String): File {
        url.openStream().use { input ->
            val file = File(target)
            file.outputStream().use { input.copyTo(it) }

            return file
        }
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(ActionManager::class.java)
    }
}

class DownloadInfo(registry: String, type: String, val version: String, val name: String) {
    /**
     * The URL of the action jar file.
     */
    val jarUrl: URL = URL("$registry$type/$name/$version/$name-$version.jar")

    /**
     * The URL of the action sha256 file.
     */
    val sha256Url: URL = URL("$registry$type/$name/$version/$name-$version.sha256")

    companion object {
        private const val ACTION_NAME_REGEX = "([a-zA-Z]+)/([a-zA-Z]+)@([a-zA-Z0-9.-]+)"
        private val logger: Logger = LoggerFactory.getLogger(DownloadInfo::class.java)

        /**
         * Create download information from action name.
         * @param registry The registry URL, default will be: https://registry.archguard.org/
         * @param actionName The action name, format: type/name@version
         */
        fun from(registry: String, actionName: String): DownloadInfo? {
            if (!verifyActionName(actionName)) {
                logger.error("Invalid action name: $actionName")
                return null
            }

            val (type, name, version) = Regex(ACTION_NAME_REGEX).find(actionName)!!.destructured
            return DownloadInfo(registry, type, version, name)
        }

        private fun verifyActionName(name: String): Boolean {
            return name.matches(Regex(ACTION_NAME_REGEX))
        }
    }
}

