package org.archguard.runner.pipeline

import kotlinx.serialization.Serializable
import java.io.File

@Serializable
data class UsesAction(
    var type: String = "",
    var name: String = "",
    var version: String = "",
) {
    private fun path() = "${this.name}/${this.version}"

    /**
     * action name to filename without extname: setup-0.1.0-SNAPSHOT
     */
    private fun filename() = "${this.name}-${this.version}"

    fun filename(ext: String) = "${this.name}-${this.version}.${ext}"

    /**
     * for example: `actions/setup/0.1.0-SNAPSHOT/setup-0.1.0-SNAPSHOT.jar`
     */
    fun fullUrl(ext: String) = "${this.type}/${this.path()}/${this.filename()}.${ext}"

    /**
     * for example: `actions/setup.json`
     */
    fun metadata() = "${this.type}/${this.name}.json"

    companion object {
        private const val NAME_REGEX = "([a-zA-Z]+)/([a-zA-Z-]+)@([a-zA-Z0-9.-]+)"
        private var cache = mutableMapOf<String, UsesAction>()

        fun verifyActionName(actionName: String): Boolean {
            return actionName.matches(Regex(NAME_REGEX))
        }

        fun from(actionName: String): UsesAction? {
            if (!verifyActionName(actionName)) {
                return null
            }

            return when {
                cache.containsKey(actionName) -> {
                    cache[actionName]!!
                }

                else -> {
                    val (type, name, version) = Regex(NAME_REGEX).find(actionName)!!.destructured
                    UsesAction(type, name, version).also {
                        cache[actionName] = it
                    }
                }
            }
        }
    }
}
