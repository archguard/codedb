package org.archguard.codedb.factor.meta

import org.archguard.codedb.factor.meta.model.DeadlineItem

/**
 * A TODO means an item had :name, :description, :level, :deadline, :assignee, :status, :created_at, :updated_at ?
 */
open class Todo (
    val name: String = "",
    val description: String = "",
) : DeadlineItem() {

}

