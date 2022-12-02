package org.archguard.codedb.factor.meta.model

import kotlinx.datetime.*

open class DeadlineItem(
    val startDate: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC),
    val endDate: LocalDateTime? = null,
) {
}