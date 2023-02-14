package org.archguard.action.checkout

class CommandManager {
    private val gitEnv = mapOf(
        "GIT_TERMINAL_PROMPT" to "0", // Disable git prompt
        "GCM_INTERACTIVE" to "Never" // Disable prompting for git credential manager
    )
    private var gitPath = ""
    private var lfs = false
    private var workingDirectory = ""

//    fun execGit(
//        args: List<String>,
//        allowAllExitCodes: Boolean = false,
//        silent: Boolean = false,
//        customListeners: Map<String, (data: ByteArray) -> Unit> = mapOf()
//    ): GitOutput {
//        val result = GitOutput()
//
//        val env = mutableMapOf<String, String>()
//        env.putAll(System.getenv())
//        env.putAll(gitEnv)
//
//        val defaultListener = mapOf(
//            "stdout" to { data: ByteArray ->
//                result.stdout += data.toString(Charsets.UTF_8)
//            }
//        )
//
//        val mergedListeners = mutableMapOf<String, (data: ByteArray) -> Unit>()
//        mergedListeners.putAll(defaultListener)
//        mergedListeners.putAll(customListeners)
//
//        val options = ExecOptions(
//            cwd = workingDirectory,
//            env = env,
//            silent = silent,
//            ignoreReturnCode = allowAllExitCodes,
//            listeners = mergedListeners
//        )
//
//        val execResult = exec.exec(gitPath, args, options)
//        result.exitCode = execResult.exitCode
//        result.stdout = execResult.stdout
//
//        return result
//    }
}

data class GitOutput(
    var stdout: String = "",
    var exitCode: Int = 0
)