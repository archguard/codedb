package org.archguard.codedb.factor.code.scm

import kotlinx.serialization.Serializable
import javax.persistence.Entity
import javax.persistence.Id

@Entity
@Serializable
class ScmCommit(
    @Id
    val id: String,
    val commitTime: Long,
    val shortMessage: String,
    val committerName: String,
    val committerEmail: String,
    val repositoryId: String,
) {
    constructor() : this("", 0, "", "", "", "")
}
