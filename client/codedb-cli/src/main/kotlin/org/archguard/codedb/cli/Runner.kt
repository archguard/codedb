package org.archguard.codedb.cli

import com.github.ajalt.clikt.core.CliktCommand

fun main(args: Array<String>) = Runner().main(args)
class Runner : CliktCommand(help = "codedb clui") {
    override fun run() {
        echo("Hello, World!")
    }
}
