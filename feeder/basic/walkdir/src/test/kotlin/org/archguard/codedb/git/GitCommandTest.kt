package org.archguard.codedb.git

import org.archguard.codedb.Workdir
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class GitCommandTest {
    @Test
    @Disabled
    fun test() {
        val gitCommand = GitCommand()
        val target = Workdir().dir("demo").toFile()
        println("target: $target")
        gitCommand.clone("https://github.com/archguard/ddd-monolithic-code-sample", target)
    }
}