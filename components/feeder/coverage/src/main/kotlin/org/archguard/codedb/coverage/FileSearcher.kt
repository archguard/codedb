package org.archguard.codedb.coverage

interface FileSearcher {
    /**
     * @param filename
     * @return true if the file is a coverage file
     */
    fun isMatch(filename: String): Boolean

    fun analyze(filename: String): FileSearchResult
}

// todo: binding to model
class FileSearchResult {

}
