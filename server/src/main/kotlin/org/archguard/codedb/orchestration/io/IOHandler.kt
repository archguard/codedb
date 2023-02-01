package org.archguard.codedb.orchestration.io

class IOHandler {
    constructor()

    fun database(tableName: String): Boolean {
        return true
    }

    fun file(fileName: String): Boolean {
        return true
    }

    // output type: dr
    fun dir(dirName: String): Boolean {
        return true
    }

    // todo: add streaming for http
    fun http(url: String, method: HttpMethod, data: Any): Boolean {
        return true
    }
}