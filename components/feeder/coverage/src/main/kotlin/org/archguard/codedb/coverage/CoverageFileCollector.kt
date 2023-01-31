package org.archguard.codedb.coverage

import org.archguard.codedb.core.WalkerCollector

val jacocoRules = listOf("jacoco*.xml", "*Jacoco*.xml")
val cloverRules = listOf("clover.xml")
val gcovRules = listOf("gcov.info")
val lcovRules = listOf("lcov.info")

val coverageFileRules = listOf(
    "*coverage*.*",
    "*.clover",
    "*.codecov.*",
    "*.gcov",
    "*.lcov",
    "*.lst",
    "cobertura.xml",
    "codecov.*",
    "cover.out",
    "codecov-result.json",
    "coverage-final.json",
    "excoveralls.json",
    "lcov.dat",
    "luacov.report.out",
    "naxsi.info",
    "nosetests.xml",
    "report.xml"
) + jacocoRules + cloverRules + gcovRules + lcovRules

// poc: can be refs by: https://codecov.io/bash
class CoverageFileCollector : WalkerCollector() {
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
                isMatch(cloverRules, it) -> println("clover: $it")
                isMatch(gcovRules, it) -> println("clover: $it")
                isMatch(lcovRules, it) -> println("clover: $it")
                else -> {
                    println("else: $it")
                }
            }
        }
    }
}

