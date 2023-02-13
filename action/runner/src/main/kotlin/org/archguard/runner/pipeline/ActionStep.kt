package org.archguard.runner.pipeline

import kotlinx.serialization.Serializable
import org.archguard.runner.runner.DownloadInfo
import org.archguard.runner.serial.Scalar
import java.io.File

@Serializable
data class ActionStep(
    /**
     * for plugin action, the uses is the plugin name.
     */
    @Deprecated("use `uses` instead")
    var name: String = "",
    var description: String = "",
    var enabled: Boolean = true,
    var uses: String = "",
    // plugin config
    var with: HashMap<String, Scalar> = hashMapOf(),

    // todo: add for support
    var output: String = "",
    var outputDir: String = "",
) {

    /**
     * convert to command line string, for examples:
     * ```yaml
     * with:
     *  key: value
     *  key2: [value1, value2]
     * ```
     *
     * with output:
     *
     * ```bash
     * --key value --key2 value1 --key2 value2
     * ```
     */
    fun toCommandLine(): String {
        val command = mutableListOf<String>()
        with
            .toSortedMap(compareByDescending { it })
            .forEach { (key, value) ->
                when (value) {
                    is Scalar.List -> {
                        value.value.forEach {
                            command.add("--$key")
                            command.add(it.toString())
                        }
                    }

                    else -> {
                        command.add("--$key")
                        command.add(value.toString())
                    }
                }
            }

        return command.joinToString(" ")
    }
}

@Serializable
data class ActionName(
    var type: String = "",
    var name: String = "",
    var version: String = "",
) {
    private fun path() = "${this.name}/${this.version}"

    /**
     * action name to filename without extname: setup-0.1.0-SNAPSHOT
     */
    fun filename() = "${this.name}-${this.version}"
    fun filename(ext: String) = "${this.name}-${this.version}.${ext}"

    /**
     * for example: `actions/setup/0.1.0-SNAPSHOT/setup-0.1.0-SNAPSHOT.jar`
     */
    fun fullUrl(ext: String) = "${this.type}/${this.path()}/${this.filename()}.${ext}"

    /**
     * to support different OSs.
     */
    fun fullpath(ext: String) = "${this.type}${File.separator}${this.path()}${File.separator}${this.filename()}.${ext}"

    companion object {
        private const val NAME_REGEX = "([a-zA-Z]+)/([a-zA-Z-]+)@([a-zA-Z0-9.-]+)"
        private var cache = mutableMapOf<String, ActionName>()

        fun verifyActionName(actionName: String): Boolean {
            return actionName.matches(Regex(NAME_REGEX))
        }

        fun from(actionName: String): ActionName? {
            if (!verifyActionName(actionName)) {
                return null
            }

            return when {
                cache.containsKey(actionName) -> {
                    cache[actionName]!!
                }

                else -> {
                    val (type, name, version) = Regex(NAME_REGEX).find(actionName)!!.destructured
                    ActionName(type, name, version).also {
                        cache[actionName] = it
                    }
                }
            }
        }
    }
}