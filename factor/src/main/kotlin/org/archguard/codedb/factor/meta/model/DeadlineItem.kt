package org.archguard.codedb.factor.meta.model

import kotlinx.datetime.*
import javax.persistence.Column
import javax.persistence.MappedSuperclass

/**
 * A deadline item has start date, end date, and duration.
 */
@MappedSuperclass
open class DeadlineItem(
    @Column(columnDefinition = "TIMESTAMP")
    val startDate: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC),
    val endDate: LocalDateTime? = null
) {

}