package org.archguard.runner.pipeline

import kotlinx.serialization.Serializable
import org.archguard.runner.serial.Scalar

@Serializable
data class ActionStep(
    /**
     * it's displayName, if you want to access actionName, please use `uses` instead
     */
    @Deprecated("use `uses` instead")
    var name: String = "",
    var description: String = "",
    var enabled: Boolean = true,
    var uses: String = "",
    // plugin config
    var with: HashMap<String, Scalar> = hashMapOf(),

    // script file for running
    var run: String = "",

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
    fun toCommandList(config: ActionEnv = ActionEnv()): List<String> {
        val command = mutableListOf<String>()
        with.forEach { (key, value) ->
            when (value) {
                is Scalar.List -> {
                    value.value.forEach {
                        command.add("--$key")
                        command.add(it.variable(config))
                    }
                }

                else -> {
                    command.add("--$key")
                    command.add(value.variable(config))
                }
            }
        }

        return command
    }
}

