package org.archguard.codedb.factor.governance.gate

import org.archguard.codedb.factor.uuid
import javax.persistence.Entity
import javax.persistence.Id

/**
 * QualityGate is a class that represents a quality gate.
 */
@Entity
class QualityGate(
    @Id
    val id: String = uuid(),
    val name: String = "",
    val description: String = "",
    val value: Double = 0.0,
) {

}
