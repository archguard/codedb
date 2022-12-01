package org.archguard.codedb.orchestration.io

class GitHandler(val commandName: String, var source: String = "", var destination: String = "") {
    fun source(input: String): GitHandler {
        source = input
        return this
    }

    // todo: auto generate this
    fun target(output: String = ""): GitHandler {
        destination = output
        return this
    }

    fun execute() {
        println("git $commandName $source $destination")
    }
}