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
                        command.add(replaceVariable(it, config))
                    }
                }

                else -> {
                    command.add("--$key")
                    command.add(replaceVariable(value, config))
                }
            }
        }

        return command
    }

    /**
     * replace variable with environment variable
     */
    private fun replaceVariable(value: Scalar, config: ActionEnv): String {
        val originValue = value.toString()

        val prefix = "\${{"
        val suffix = "}}"
        if (originValue.startsWith(prefix) && originValue.endsWith(suffix)) {
            val variable = originValue.substring(prefix.length, originValue.length - suffix.length)
            return when (variable.trim()) {
                "config.server.url" -> config.server.url
                else -> originValue
            }
        }


        return originValue
    }
}

private fun String.lowerDashCase(vararg ignore: Char): String {
    return formatLowerCase(this, '-', ignore)
}

private fun formatLowerCase(input: String, separator: Char, ignore: CharArray) =
    formatCase(input, separator, ignore, false)

/**
 * Unlicense license
 * based on https://github.com/Fleshgrinder/kotlin-case-format
 */
private fun formatCase(input: String, separator: Char, ignore: CharArray, upperCase: Boolean) =
    if (input.isEmpty()) input else StringBuilder(input.length).also {
        var seenSeparator = true
        var seenUpperCase = false

        input.forEach { c ->
            when (c) {
                in ignore -> {
                    it.append(c)
                    seenSeparator = true
                    seenUpperCase = false
                }

                in '0'..'9' -> {
                    it.append(c)
                    seenSeparator = false
                    seenUpperCase = false
                }

                in 'a'..'z' -> {
                    it.append(if (upperCase) c.toUpperCase() else c)
                    seenSeparator = false
                    seenUpperCase = false
                }

                in 'A'..'Z' -> {
                    if (!seenSeparator && !seenUpperCase) it.append(separator)
                    it.append(if (upperCase) c else c.toLowerCase())
                    seenSeparator = false
                    seenUpperCase = true
                }

                else -> {
                    if (!seenSeparator || !seenUpperCase) it.append(separator)
                    seenSeparator = true
                    seenUpperCase = false
                }
            }
        }
    }.toString()