package org.archguard.action.checkout

import org.archguard.action.exec.CommandArgs
import org.archguard.action.exec.CommandSetting
import java.io.File.separator

class GitSourceSettings(
    val repository: String,
    val branch: String = "master",
    var ref: String = "refs/heads/$branch",
    val authToken: String = "",
    val sshKey: String = "",
    val serverSide: Boolean = false,
    val submodule: Boolean = false,
    var fetchDepth: Int = 0,
    var workdir: String = "",
) : CommandSetting {
    val gitServerUrl: String = "https://github.com"
    val nestedSubmodules: Boolean = false
    val commit: String = ""
    var repositoryPath = repository.substringAfterLast("/")
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
                serverSide = argsMap["--server-side"]?.toBoolean() ?: false,
                submodule = argsMap["--submodule"]?.toBoolean() ?: false,
                workdir = argsMap["--workdir"] ?: ".tmp",
            )
        }
    }
}