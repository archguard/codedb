package org.archguard.codedb.coverage.clover

import org.slf4j.LoggerFactory
import com.atlassian.clover.model.CoverageDataPoint
import com.atlassian.clover.model.XmlConverter
import java.io.File

/**
 * Coverage Model ?
 */
class OpenCloverService {
    companion object {
        val logger = LoggerFactory.getLogger(OpenCloverService::class.java)
    }

    fun parse(file: File): CoverageDataPoint? {
        try {
            return XmlConverter.getFromXmlFile(file, XmlConverter.FILE_LEVEL)
        } catch (e: Exception) {
            logger.error("Error parsing clover file: ${file.absolutePath}", e)
            return null
        }
    }
}