package org.archguard.action.checkout

import org.archguard.action.exec.CommandArgs

class GitSourceSettings(
    val repository: String,
    val branch: String = "master",
    val ref: String = "refs/heads/$branch",
    val authToken: String = "",
    val sshKey: String = "",
) {

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