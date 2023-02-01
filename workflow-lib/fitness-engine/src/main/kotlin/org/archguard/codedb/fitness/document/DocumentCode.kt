package org.archguard.codedb.fitness.document

abstract class DocumentCode {
    abstract fun hasReadme(): Boolean

    abstract fun hasAdr(): Boolean
}