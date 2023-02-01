package org.archguard.codedb.fitness.document

abstract class DocumentCode {
    /**
     * @return true if the code has a readme file
     */
    abstract fun hasReadmeFile()

    /**
     * @return true if the code has a adr file
     */
    abstract fun hasAdr(): Boolean
}
