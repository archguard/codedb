package org.archguard.codedb.metric.oo

import kotlinx.serialization.Serializable
import org.archguard.action.exec.CommandArgs
import org.archguard.action.exec.CommandSetting

@Serializable
data class OoMetricSetting(
    val input: String,
    val output: String,
) : CommandSetting {
    companion object {
        fun fromArgs(args: Array<String>): OoMetricSetting {
            val argsMap = CommandArgs.argsToMap(args)

            return OoMetricSetting(
                input = argsMap["--input"] ?: "",
                output = argsMap["--output"] ?: "",
            )
        }
    }
}