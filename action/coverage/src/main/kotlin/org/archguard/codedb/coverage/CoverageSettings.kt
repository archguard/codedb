package org.archguard.codedb.coverage

class CoverageSettings(val workdir: String, val outputDir: String) {
    companion object {
        fun from(argsToMap: Map<String, String>): CoverageSettings {
            return CoverageSettings(
                workdir = argsToMap["workdir"] ?: "",
                outputDir = argsToMap["outputDir"] ?: ""
            )
        }
    }
}