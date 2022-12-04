package org.archguard.codedb.factor.meta.model

import kotlinx.datetime.*

/**
 * A deadline item has start date, end date, and duration.
 */
open class DeadlineItem(
    val startDate: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC),
    val endDate: LocalDateTime? = null
) {

}