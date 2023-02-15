package org.archguard.codedb.coverage

import com.google.bazel.Coverage
import com.google.bazel.CoverageUtil
import com.google.bazel.GcovJsonParser
import com.google.bazel.GcovParser
import com.google.bazel.LcovMergerFlags
import com.google.bazel.LcovParser
import org.archguard.action.exec.CommandArgs
import java.io.InputStream

val logger = org.slf4j.LoggerFactory.getLogger("CoverageAction")
private const val DEFAULT_PARSE_FILE_PARALLELISM = 4

fun main(args: Array<String>) {
    // println args
    val settings = CoverageSettings.from(CommandArgs.argsToMap(args))

    val arrayOf = arrayOf("--coverage_dir", settings.workdir, "--reports_file", "", "--output_file", settings.outputDir)
    var flags: LcovMergerFlags? = null
    try {
        flags = LcovMergerFlags.parseFlags(arrayOf)
    } catch (e: IllegalArgumentException) {
        logger.info(e.message)
        return
    }

    val filesInCoverageDir = CoverageUtil.getCoverageFilesInDir(flags.coverageDir())
    val coverage = Coverage.merge(
        CoverageUtil.parseFiles(
            CoverageUtil.getTracefiles(flags, filesInCoverageDir), { inputStream: InputStream? ->
                LcovParser.parse(
                    inputStream
                )
            },
            DEFAULT_PARSE_FILE_PARALLELISM
        ),
        CoverageUtil.parseFiles(
            CoverageUtil.getGcovInfoFiles(filesInCoverageDir),
            { inputStream: InputStream? -> GcovParser.parse(inputStream) }, DEFAULT_PARSE_FILE_PARALLELISM
        ),
        CoverageUtil.parseFiles(
            CoverageUtil.getGcovJsonInfoFiles(filesInCoverageDir), { inputStream: InputStream? ->
                GcovJsonParser.parse(
                    inputStream
                )
            },
            DEFAULT_PARSE_FILE_PARALLELISM
        )
    )
}
