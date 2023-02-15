package org.archguard.action.checkout

import org.archguard.action.exec.CommandArgs

class GitSourceSettings(
    val repository: String,
    val branch: String = "master",
    val serverSide: Boolean = false,
    var ref: String = "refs/heads/$branch",
    val authToken: String = "",
    val sshKey: String = "",
) {
    val gitServerUrl: String = "https://github.com"
    val fetchDepth: Int = 0
    val nestedSubmodules: Boolean = false
    val commit: String = ""
    val repositoryPath: String get() = repository.substringAfterLast("/")

    val persistCredentials: Boolean = false
    val sshStrict: Boolean = false
    val sshKnownHosts: String = ""

    companion object {
        fun fromArgs(args: Array<String>): GitSourceSettings {
            val argsMap = CommandArgs.argsToMap(args)

            return GitSourceSettings(
                repository = argsMap["--repository"] ?: "",
                branch = argsMap["--branch"] ?: "master",
                ref = argsMap["--ref"] ?: "refs/heads/master",
                authToken = argsMap["--auth-token"] ?: "",
                sshKey = argsMap["--ssh-key"] ?: "",
            )
        }
    }
}