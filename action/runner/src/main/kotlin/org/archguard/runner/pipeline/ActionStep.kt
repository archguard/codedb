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

