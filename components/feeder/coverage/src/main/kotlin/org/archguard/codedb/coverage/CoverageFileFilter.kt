package org.archguard.codedb.coverage

import org.archguard.codedb.core.BasicWalkerFilter

val jacocoRules = listOf(
    "jacoco*.xml",
    "*Jacoco*.xml",
)
val coverageFileRules = listOf(
    "*coverage*.*",
    "*.clover",
    "*.codecov.*",
    "*.gcov",
    "*.lcov",
    "*.lst",
    "clover.xml",
    "cobertura.xml",
    "codecov.*",
    "cover.out",
    "codecov-result.json",
    "coverage-final.json",
    "excoveralls.json",
    "gcov.info",
    "lcov.dat",
    "lcov.info",
    "luacov.report.out",
    "naxsi.info",
    "nosetests.xml",
    "report.xml"
) + jacocoRules

// poc: can be refs by: https://codecov.io/bash
class CoverageFileFilter : BasicWalkerFilter() {
    var collectFiles: List<String> = emptyList()

    fun isMatch(filename: String): Boolean {
        val hasMatched = isMatch(coverageFileRules, filename)

        if (hasMatched) {
            collectFiles = collectFiles.plus(filename)
        }

        return hasMatched
    }

    override fun finalize() {
        collectFiles.forEach {
            when {
                isMatch(jacocoRules, it) -> println("jacoco: $it")
                else -> {
                    println("else: $it")
                }
            }
        }
    }
}

