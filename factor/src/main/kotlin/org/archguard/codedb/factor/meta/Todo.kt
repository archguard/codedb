package org.archguard.codedb.factor.meta

import org.archguard.codedb.factor.meta.model.DeadlineItem

open class Todo (
    val name: String = "",
    val description: String = "",
) : DeadlineItem() {

}

