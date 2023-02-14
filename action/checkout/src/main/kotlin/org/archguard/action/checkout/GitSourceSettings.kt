package org.archguard.action.checkout

import org.archguard.action.exec.CommandArgs

class GitSourceSettings(
    val repository: String,
    val branch: String = "master",
    val serverSide: Boolean = false,
    var ref: String = "refs/heads/$branch",
    val token: String = "",
    val sshKey: String = "",
) {
    val fetchDepth: Int = 0
    val nestedSubmodules: Boolean = false
    val commit: String = ""
    val repositoryPath: String get() = repository.substringAfterLast("/")

    companion object {
        fun fromArgs(args: Array<String>): GitSourceSettings {
            val argsMap = CommandArgs.argsToMap(args)

            return GitSourceSettings(
                repository = argsMap["--repository"] ?: "",
                branch = argsMap["--branch"] ?: "master",
                ref = argsMap["--ref"] ?: "refs/heads/master",
                token = argsMap["--token"] ?: "",
                sshKey = argsMap["--ssh-key"] ?: "",
            )
        }
    }
}