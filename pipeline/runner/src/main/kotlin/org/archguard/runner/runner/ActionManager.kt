package org.archguard.runner.runner

import kotlinx.coroutines.runBlocking
import org.archguard.action.http.HttpClientWrapper
import org.archguard.runner.context.RunnerContext
import org.archguard.runner.pipeline.ActionDefinitionData
import org.archguard.runner.pipeline.ActionExecutionData
import org.archguard.runner.pipeline.UsesAction
import org.archguard.runner.registry.ActionRegistry
import org.archguard.runner.registry.Version
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File
import java.net.URL

class ActionManager(val context: RunnerContext) : RunnerService() {
    private val actionManifestManager = ActionManifestManager()
    private val client = HttpClientWrapper()

    fun loadActions(): ActionDefinitionData {
        val content = File(context.manifestYmlPath).readText()
        return actionManifestManager.load(content)
    }

    /**
     * Prepare actions for execution, this will download the action and prepare the environment.
     */
    fun prepareActionsAsync(actionData: ActionExecutionData) {
        // 1. create download information
        val downloadInfos: Map<String, DownloadInfo> =
            actionData.steps.mapNotNull {
                DownloadInfo.from(context.registry, it.uses)
            }.associateBy { it.usesAction.name }

        logger.info("Download actions: ${downloadInfos.map { it.value.usesAction.name }.joinToString(", ")}")

        // 2. prepare plugins dir
        val pluginsDir = File(context.pluginDirectory)
        if (!pluginsDir.exists()) {
            pluginsDir.mkdirs()
        }

        logger.info("Plugins directory: ${pluginsDir.absolutePath}")

        downloadInfos.entries.parallelStream().forEach { map ->
            // download json before download action
            downloadAction(context, map.value)
        }
    }

    private fun downloadAction(context: RunnerContext, downloadInfo: DownloadInfo) {
        executeDownload(downloadInfo, context.pluginDirectory)
    }

    // todo: add verify for sha256
    fun executeDownload(downloadInfo: DownloadInfo, targetDir: String) {
        try {
            runBlocking {
                logger.info("Start fetch registry: ${downloadInfo.usesAction}")

                val actionRegistry: ActionRegistry = client.get(downloadInfo.metaUrl)

                val versionInfos: List<Version> = actionRegistry.versions.filter {
                    it.key == downloadInfo.version
                }.entries.map { it.value }

                if (versionInfos.isEmpty()) {
                    logger.error("Version not found: ${downloadInfo.usesAction}")
                    return@runBlocking
                }

                downloadByVersion(versionInfos, targetDir, downloadInfo)
            }
        } catch (e: Exception) {
            logger.error("Failed to download action: ${downloadInfo.usesAction}, ${downloadInfo.metaUrl}", e)
        }
    }

    private suspend fun downloadByVersion(versionInfos: List<Version>, targetDir: String, downloadInfo: DownloadInfo) {
        val target = filepath(targetDir, downloadInfo, "jar")

        if (File(target).exists()) {
            logger.info("Action already downloaded: ${downloadInfo.usesAction}")
            return
        }

        val version = versionInfos[0]
        logger.info("Start downloading action by version: $version")

        val file = File(target)
        client.download(URL(version.dist.pkg), file)
        logger.info("Downloaded action: ${downloadInfo.usesAction} to ${file.absolutePath}")
    }

    /**
     * for examples: /tmp/plugins/checkout-0.1.0-SNAPSHOT.jar
     */
    private fun filepath(targetDir: String, downloadInfo: DownloadInfo, ext: String) =
        "$targetDir${File.separator}${downloadInfo.nameOnly(ext)}"

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(ActionManager::class.java)
    }
}


class DownloadInfo(registry: String, val usesAction: UsesAction) {
    val version: String = usesAction.version

    val metaUrl: URL by lazy { URL("$registry${usesAction.metadata()}") }

    /**
     * The URL of the action sha256 file.
     */
    val sha256Url: URL = URL("$registry${usesAction.fullUrl("sha256")}")

    fun nameOnly(ext: String) = usesAction.filename(ext)

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(DownloadInfo::class.java)

        /**
         * Create download information from action name.
         * @param registry The registry URL, default will be: https://registry.archguard.org/
         * @param actionNameString The action name, format: type/name@version
         */
        fun from(registry: String, actionNameString: String): DownloadInfo? {
            if (!UsesAction.verifyActionName(actionNameString)) {
                logger.error("Invalid action name: $actionNameString")
                return null
            }

            val usesAction = UsesAction.from(actionNameString)!!
            return DownloadInfo(registry, usesAction)
        }
    }
}

