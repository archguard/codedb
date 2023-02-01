package org.archguard.codedb.factor.meta

import org.archguard.codedb.factor.meta.model.DeadlineItem
import javax.persistence.MappedSuperclass

/**
 * A TODO means an item had :name, :description, :level, :deadline, :assignee, :status, :created_at, :updated_at ?
 */
@MappedSuperclass
open class Todo (
    val name: String = "",
    val description: String = "",
) : DeadlineItem() {

}

