package org.archguard.runner.pipeline

import kotlinx.serialization.Serializable
import org.archguard.runner.serial.Scalar

@Serializable
data class ActionStep(
    var name: String = "",
    var description: String = "",
    var enabled: Boolean = true,
    var uses: String = "",
    // plugin config
    var with: HashMap<String, Scalar> = hashMapOf(),
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


    /**
    for example: `actions/setup/0.1.0-SNAPSHOT/setup-0.1.0-SNAPSHOT.jar`
     */
    fun fullFilepath(ext: String) = "${this.type}/${this.path()}/${this.filename()}.${ext}"

    companion object {
        private const val NAME_REGEX = "([a-zA-Z]+)/([a-zA-Z]+)@([a-zA-Z0-9.-]+)"

        fun verifyActionName(actionName: String): Boolean {
            return actionName.matches(Regex(NAME_REGEX))
        }

        fun from(actionName: String): ActionName {
            val (type, name, version) = Regex(NAME_REGEX).find(actionName)!!.destructured
            return ActionName(type, name, version)
        }
    }
}