package org.archguard.codedb.factor.code.scm

import kotlinx.serialization.Serializable
import javax.persistence.Entity
import javax.persistence.Id

@Entity
@Serializable
class ScmPathChangeCount(
    @Id
    val id: String,
    val path: String,
    val changes: Int,
    val lineCount: Long,
    val language: String,
) {
    constructor() : this("", "", 0, 0, "")
}
