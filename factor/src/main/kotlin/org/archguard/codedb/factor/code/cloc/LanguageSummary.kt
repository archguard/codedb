package org.archguard.codedb.factor.code.cloc

import kotlinx.serialization.Serializable
import javax.persistence.Entity
import javax.persistence.Id

@Serializable
@Entity
class LanguageSummary(
    @Id
    val id: String,
    var name: String = "",
    var bytes: Long = 0,
    var codeBytes: Long = 0,
    var lines: Long = 0,
    var code: Long = 0,
    var comment: Long = 0,
    var blank: Long = 0,
    var complexity: Long = 0,
    var count: Long = 0,
    var weightedComplexity: Double = 0.0,
) {
    constructor() : this("")
}
