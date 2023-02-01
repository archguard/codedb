package org.archguard.codedb.factor.governance.linting

import kotlinx.serialization.Serializable
import org.archguard.codedb.factor.uuid
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.MapKeyColumn

@Serializable
@Entity
class IssuePosition(
    @Id
    val id: String = uuid(),
    var startLine: Int = 0,
    var startColumn: Int = 0,
    var endLine: Int = 0,
    var endColumn: Int = 0,

    @MapKeyColumn(name = "additions")
    @Column(name = "value")
    var additions: Map<String, String> = mapOf(),
) {
    override fun toString(): String {
        return "IssuePosition(startLine=$startLine, startColumn=$startColumn, endLine=$endLine, endColumn=$endColumn, additions=$additions)"
    }
}

