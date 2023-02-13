package org.archguard.runner.runner

import org.archguard.runner.context.RunnerContext
import org.archguard.runner.pipeline.ActionDefinitionData
import org.archguard.runner.pipeline.ActionExecutionData
import org.archguard.runner.pipeline.ActionName
import org.jetbrains.annotations.TestOnly
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File
import java.net.URL

class ActionManager(val context: RunnerContext) : RunnerService() {
    val actionManifestManager = ActionManifestManager()
    fun loadActions(): ActionDefinitionData {
        val content = File(context.manifestYmlPath).readText()
        return actionManifestManager.load(content)
    }

    /**
     * todo: make api to sync and cached
     * Prepare actions for execution, this will download the action and prepare the environment.
     */
    fun prepareActionsAsync(actionData: ActionExecutionData) {
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
        val jarFile = downloadFile(downloadInfo.jarUrl, filepath(targetDir, downloadInfo, "jar"))
        // todo: add verify for sha256
//        val shaFile = downloadFile(downloadInfo.sha256Url, fileOutput(targetDir, downloadInfo, ".sha256"))
    }

    /**
     * for examples: /tmp/plugins/checkout-0.1.0-SNAPSHOT.jar
     */
    private fun filepath(targetDir: String, downloadInfo: DownloadInfo, extName: String) =
        "$targetDir${File.separator}${downloadInfo.filename(extName)}$"

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


class DownloadInfo(registry: String, val actionName: ActionName) {

    /**
     * The URL of the action jar file.
     */
    val jarUrl: URL by lazy { URL("$registry${actionName.fullFilepath("jar")}") }

    /**
     * The URL of the action sha256 file.
     */
    val sha256Url: URL = URL("$registry${actionName.fullFilepath("sha256")}")

    fun filename(ext: String) = actionName.fullFilepath(ext)

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(DownloadInfo::class.java)

        /**
         * Create download information from action name.
         * @param registry The registry URL, default will be: https://registry.archguard.org/
         * @param actionNameString The action name, format: type/name@version
         */
        fun from(registry: String, actionNameString: String): DownloadInfo? {
            if (!ActionName.verifyActionName(actionNameString)) {
                logger.error("Invalid action name: $actionNameString")
                return null
            }

            val actionName = ActionName.from(actionNameString)
            return DownloadInfo(registry, actionName)
        }
    }
}

