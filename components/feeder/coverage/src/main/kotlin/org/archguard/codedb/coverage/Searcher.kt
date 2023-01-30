package org.archguard.codedb.coverage

import com.google.re2j.Matcher
import java.nio.file.FileSystems

// poc: can be refs by: https://codecov.io/bash
class Searcher {
    var coverageFiles = listOf(
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
        "jacoco*.xml",
        "*Jacoco*.xml",
        "lcov.dat",
        "lcov.info",
        "luacov.report.out",
        "naxsi.info",
        "nosetests.xml",
        "report.xml"
    )

    fun isMatch(filename: String): Boolean {
        val fileSystem = FileSystems.getDefault()
        return coverageFiles.any { fileSystem.getPathMatcher("glob:$it").matches(fileSystem.getPath(filename)) }
    }
}
