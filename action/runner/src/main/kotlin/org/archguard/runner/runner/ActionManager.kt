package org.archguard.runner.runner

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.archguard.runner.context.RunnerContext
import org.archguard.runner.pipeline.ActionDefinitionData
import org.archguard.runner.pipeline.ActionExecutionData
import org.archguard.runner.pipeline.ActionName
import org.archguard.runner.registry.ActionRegistry
import org.archguard.runner.registry.Version
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File
import java.net.URL

private const val TIMEOUT_MILLIS = 1000 * 60 * 5L

class ActionManager(val context: RunnerContext) : RunnerService() {
    private val actionManifestManager = ActionManifestManager()
    private val client = HttpClient(CIO) {
        install(HttpTimeout) {
            requestTimeoutMillis = TIMEOUT_MILLIS
        }
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
    }

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
            actionData.steps.mapNotNull {
                DownloadInfo.from(context.registry, it.uses)
            }

        logger.info("Download actions: ${downloadInfos.map { it.actionName }}")

        // 2. prepare plugins dir
        val pluginsDir = File(context.pluginDirectory)
        if (!pluginsDir.exists()) {
            pluginsDir.mkdirs()
        }

        logger.info("Plugins directory: ${pluginsDir.absolutePath}")

        downloadInfos.parallelStream().forEach { downloadInfo ->
            // download json before download action
            downloadAction(context, downloadInfo)
        }
    }

    private fun downloadAction(context: RunnerContext, downloadInfo: DownloadInfo) {
        executeDownload(downloadInfo, context.pluginDirectory)
    }

    // todo: add verify for sha256
    fun executeDownload(downloadInfo: DownloadInfo, targetDir: String) {
        try {
            runBlocking {
                logger.info("Start fetch registry: ${downloadInfo.actionName}")

                val actionRegistry: ActionRegistry = client.get(downloadInfo.metaUrl).body()

                val versionInfos: List<Version> = actionRegistry.versions.filter {
                    it.key == downloadInfo.version
                }.entries.map { it.value }

                if (versionInfos.isEmpty()) {
                    logger.error("Version not found: ${downloadInfo.actionName}")
                    return@runBlocking
                }

                downloadByVersion(versionInfos, targetDir, downloadInfo)
            }
        } catch (e: Exception) {
            logger.error("Failed to download action: ${downloadInfo.actionName}", e)
        }
    }

    private suspend fun downloadByVersion(versionInfos: List<Version>, targetDir: String, downloadInfo: DownloadInfo) {
        val target = filepath(targetDir, downloadInfo, "jar")

        if (File(target).exists()) {
            logger.info("Action already downloaded: ${downloadInfo.actionName}")
            return
        }

        val version = versionInfos[0]
        logger.info("Start downloading action by version: $version")

        val jarFile = downloadFile(URL(version.dist.pkg), target)
        logger.info("Downloaded action: ${downloadInfo.actionName} to ${jarFile.absolutePath}")
    }

    /**
     * for examples: /tmp/plugins/checkout-0.1.0-SNAPSHOT.jar
     */
    private fun filepath(targetDir: String, downloadInfo: DownloadInfo, ext: String) =
        "$targetDir${File.separator}${downloadInfo.nameOnly(ext)}"

    private suspend fun downloadFile(url: URL, target: String): File {
        val httpResponse: HttpResponse = client.get(url) {
            onDownload { bytesSentTotal, contentLength ->
                if (bytesSentTotal % (1024) == 0L) {
                    logger.info("Downloaded ${bytesSentTotal / 1024}k of ${contentLength / 1024}k")
                }
            }
        }

        val responseBody: ByteArray = httpResponse.body()
        val file = File(target)
        file.writeBytes(responseBody)
        return file
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(ActionManager::class.java)
    }
}


class DownloadInfo(registry: String, val actionName: ActionName) {
    val version: String = actionName.version

    val metaUrl: URL by lazy { URL("$registry${actionName.metadata()}") }

    /**
     * The URL of the action sha256 file.
     */
    val sha256Url: URL = URL("$registry${actionName.fullUrl("sha256")}")

    fun nameOnly(ext: String) = actionName.filename(ext)

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

            val actionName = ActionName.from(actionNameString)!!
            return DownloadInfo(registry, actionName)
        }
    }
}

