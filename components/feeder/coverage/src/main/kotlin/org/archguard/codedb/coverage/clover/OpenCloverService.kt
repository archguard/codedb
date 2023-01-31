package org.archguard.codedb.coverage.clover

import org.slf4j.LoggerFactory
import java.io.File

/**
 * Coverage Model ?
 */
class OpenCloverService {
    companion object {
        val logger = LoggerFactory.getLogger(OpenCloverService::class.java)
    }

    fun parse(file: File): CoverageDataPoint? {
        return null
    }
}