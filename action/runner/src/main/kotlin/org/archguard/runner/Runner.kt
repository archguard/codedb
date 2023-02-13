package org.archguard.runner

import com.github.ajalt.clikt.core.CliktCommand
fun main(args: Array<String>) = Runner().main(args)
class Runner : CliktCommand(help = "ArchGuard Action Runner") {
    override fun run() {
        echo("Hello, World!")
    }
}
