package org.archguard.runner.pipeline

import org.archguard.runner.serial.Scalar

fun Scalar.variable(config: ActionEnv): String {
    return when (this) {
        is Scalar.String -> replaceVariable(this, config).toString()
        else -> this.toString()
    }
}

fun Scalar.fillVariable(config: ActionEnv): Scalar {
    return when (this) {
        is Scalar.String -> replaceVariable(this, config)
        else -> this
    }
}

/**
 * replace variable with environment variable
 */
fun replaceVariable(value: Scalar, config: ActionEnv): Scalar {
    val string = value.toString()

    val prefix = "\${{"
    val suffix = "}}"
    if (string.startsWith(prefix) && string.endsWith(suffix)) {
        val variable = string.substring(prefix.length, string.length - suffix.length)
        return when (variable.trim()) {
            "env.server.url" -> Scalar.String(config.server.url)
            else -> value
        }
    }

    return value
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
