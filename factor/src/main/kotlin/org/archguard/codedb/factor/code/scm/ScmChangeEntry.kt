package org.archguard.codedb.factor.code.scm

import kotlinx.serialization.Serializable
import javax.persistence.Entity
import javax.persistence.Id

@Serializable
@Entity
class ScmChangeEntry(
    @Id
    val id: String,
    val oldPath: String,
    val newPath: String,
    val commitTime: Long,
    val cognitiveComplexity: Int,
    val changeMode: String,
    val commitId: String,
    val committer: String,
    val lineAdded: Int,
    val lineDeleted: Int
) {
    constructor() : this("", "", "", 0, 0, "", "", "", 0, 0)
}