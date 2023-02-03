package org.archguard.codedb.fitness.slice.document

abstract class DocumentCode {
    /**
     * @return true if the code has a readme file
     */
    abstract fun hasReadmeFile(): Boolean

    /**
     * has architecture decision record
     */
    abstract fun hasDecisionRecord(): Boolean
}
