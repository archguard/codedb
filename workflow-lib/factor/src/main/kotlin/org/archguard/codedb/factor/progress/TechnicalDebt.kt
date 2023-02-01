package org.archguard.codedb.factor.progress

import org.archguard.codedb.factor.meta.Todo
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@Entity
@MappedSuperclass
class TechnicalDebt(
    @Id
    val id: String = UUID.randomUUID().toString(),
) : Todo() {
}