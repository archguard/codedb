package org.archguard.codedb.coverage

import org.archguard.action.exec.CommandSetting

class CoverageSettings(val workdir: String, val outputDir: String) : CommandSetting {
    companion object {
        fun from(argsToMap: Map<String, String>): CoverageSettings {
            return CoverageSettings(
                workdir = argsToMap["workdir"] ?: "",
                outputDir = argsToMap["outputDir"] ?: ""
            )
        }
    }
}