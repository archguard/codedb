package org.archguard.runner

import org.archguard.runner.context.RunnerContext
import org.archguard.runner.pipeline.ActionExecutionData
import org.archguard.runner.pipeline.ActionStep
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

        downloadInfos.forEach { downloadInfo ->
            // 2. download action
            downloadAction(context, downloadInfo)
        }
    }

    //
    private fun downloadAction(context: RunnerContext, downloadInfo: DownloadInfo) {
        val targetDir = context.pluginDirectory
        val jarUrl = downloadInfo.jarUrl
        var sha256Url = downloadInfo.sha256Url

        // 1. download action jar
        val jarFile = downloadFile(jarUrl, targetDir)
    }

    private fun downloadFile(url: URL, target: String) {
        url.openStream().use { input ->
            val file = File(target)
            file.outputStream().use { input.copyTo(it) }
        }
    }
}

class DownloadInfo(val registry: String, val type: String, val version: String, val name: String) {
    /**
     * The URL of the action jar file.
     */
    val jarUrl: URL = URL("$registry$type/$name/$version/$name.jar")

    /**
     * The URL of the action sha256 file.
     */
    val sha256Url: URL = URL("$registry$type/$name/$version/$name.sha256")

    companion object {
        private const val ACTION_NAME_REGEX = "([a-z]+)/([a-z]+)@([a-z0-9.]+)"
        private val logger: Logger = LoggerFactory.getLogger(DownloadInfo::class.java)

        /**
         * Create download information from action name.
         * @param registry The registry URL, default will be: https://registry.archguard.org/
         * @param actionName The action name, format: type/name@version
         */
        fun from(registry: String, actionName: String): DownloadInfo? {
            if (!verifyActionName(actionName)) {
                logger.error("Invalid action name: ${actionName}")
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

