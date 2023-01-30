package org.archguard.codedb.coverage

import java.nio.file.FileSystems

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
class CoverageFileFilter {
    var collectFiles: List<String> = emptyList()
    val fileSystem = FileSystems.getDefault()

    fun isMatch(filename: String): Boolean {
        return match(coverageFileRules, filename)
    }

    private fun match(rules: List<String>, filename: String) = rules.any { match(it, filename) }

    private fun match(rule: String, filename: String) =
        fileSystem.getPathMatcher("glob:$rule").matches(fileSystem.getPath(filename))

    fun check(filename: String) {
        if (isMatch(filename)) {
            collectFiles = collectFiles.plus(filename)
        }
    }

    fun finalize() {
        collectFiles.forEach {
            when {
                match(jacocoRules, it) -> println("jacoco: $it")
                else -> {
                    println("else: $it")
                }
            }
        }
    }
}
