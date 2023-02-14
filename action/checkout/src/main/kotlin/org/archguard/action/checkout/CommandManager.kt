package org.archguard.action.checkout

import org.archguard.action.exec.Command
import org.archguard.action.exec.ExecListeners
import org.archguard.action.exec.ExecOptions

class CommandManager {
    private val gitEnv = mapOf(
        "GIT_TERMINAL_PROMPT" to "0", // Disable git prompt
        "GCM_INTERACTIVE" to "Never" // Disable prompting for git credential manager
    )

    private var gitPath = "git"

    private var lfs = false

    private var workingDirectory = "."

    private val exec = Command()

    /**
     * Initializes the git repository.
     */
    fun init(): GitOutput {
        return execGit(listOf("init", workingDirectory))
    }

    /**
     * Gets the latest commit.
     */
    fun log(format: String? = null): GitOutput {
        val args = if (format != null) {
            listOf("log", "-1", format)
        } else {
            listOf("log", "-1")
        }
        val silent = format != null
        return execGit(args, false, silent)
    }

    private fun execGit(args: List<String>, allowAllExitCodes: Boolean = false, silent: Boolean = false): GitOutput {
        val result = GitOutput()

        val env = mutableMapOf<String, String>()
        for ((key, value) in System.getenv()) {
            env[key] = value
        }
        for ((key, value) in gitEnv) {
            env[key] = value
        }

        val stdout = mutableListOf<String>()
        val options = ExecOptions(
            cwd = workingDirectory,
            env = env,
            silent = silent,
            ignoreReturnCode = allowAllExitCodes,
            listeners = object : ExecListeners {
                override fun stdout(data: String) {
                    stdout.add(data)
                }
            }
        )

        result.exitCode = exec.exec(gitPath, args, options)
        result.stdout = stdout.joinToString("")

        return result
    }
}

data class GitOutput(
    var stdout: String = "",
    var exitCode: Int = 0
)
