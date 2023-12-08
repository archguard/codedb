package org.archguard.action.checkout

import org.archguard.action.checkout.helper.RefHelper
import org.archguard.action.checkout.helper.RetryHelper
import org.archguard.action.exec.Command
import org.archguard.action.exec.ExecOptions
import org.archguard.action.exec.StringListExecListeners
import org.jetbrains.annotations.TestOnly
import java.io.File

class GitCommandManager(var workingDirectory: String = ".", private var lfs: Boolean = false) {
    private val gitEnv: MutableMap<String, String> = mutableMapOf(
        "GIT_TERMINAL_PROMPT" to "0", // Disable git prompt
        "GCM_INTERACTIVE" to "Never" // Disable prompting for git credential manager
    )

    // enable for custom git path
    var gitPath = "git"

    private val exec = Command()

    fun init(): GitOutput {
        return execGit(listOf("init", workingDirectory))
    }

    fun remoteAdd(remoteName: String, remoteUrl: String) {
        execGit(listOf("remote", "add", remoteName, remoteUrl))
    }

    fun branchDelete(remote: Boolean, branch: String): GitOutput {
        val args = mutableListOf("branch", "--delete", "--force")
        if (remote) {
            args.add("--remote")
        }
        args.add(branch)
        return execGit(args)
    }

    fun branchExists(remote: Boolean, pattern: String): Boolean {
        val args = mutableListOf("branch", "--list")
        if (remote) {
            args.add("--remote")
        }
        args.add(pattern)
        val output = execGit(args)
        return output.stdout.trim().isNotEmpty()
    }

    fun branchList(remote: Boolean): List<String> {
        val args = mutableListOf("rev-parse", "--symbolic-full-name")
        if (remote) {
            args.add("--remotes=origin")
        } else {
            args.add("--branches")
        }

        val output = execGit(args)

        return parseBranchList(output.stdout.lines())
    }

    @TestOnly
    fun parseBranchList(lines: List<String>): MutableList<String> {
        val result = mutableListOf<String>()

        for (branch in lines) {
            if (branch.startsWith("refs/heads/")) {
                result.add(branch.substring("refs/heads/".length))
            } else if (branch.startsWith("refs/remotes/")) {
                result.add(branch.substring("refs/remotes/".length))
            }
        }

        return result
    }

    fun checkout(ref: String, startPoint: String? = null): GitOutput {
        val args = mutableListOf("checkout", "--progress", "--force")

        if (startPoint != null) {
            args.addAll(listOf("-B", ref, startPoint))
        } else {
            args.add(ref)
        }

        return execGit(args)
    }

    fun checkoutDetach(): GitOutput {
        val args = listOf("checkout", "--detach")
        return execGit(args)
    }

    fun config(configKey: String, configValue: String, globalConfig: Boolean = false, add: Boolean = false): GitOutput {
        val args = mutableListOf("config", if (globalConfig) "--global" else "--local")
        if (add) {
            args.add("--add")
        }

        args.addAll(listOf(configKey, configValue))
        return execGit(args)
    }

    fun tryDisableAutomaticGarbageCollection(): Boolean {
        val output = execGit(listOf("config", "--local", "gc.auto", "0"), true)
        return output.exitCode == 0
    }

    fun configExists(configKey: String, globalConfig: Boolean = false): Boolean {
        val pattern = Regex.escape(configKey)
        val scope = if (globalConfig) "--global" else "--local"
        val output = execGit(listOf("config", scope, "--name-only", "--get-regexp", pattern), true)
        return output.exitCode == 0
    }


    private val refHelper: RefHelper = RefHelper()
    private val retryHelper: RetryHelper = RetryHelper()

    fun fetch(refSpec: List<String>, fetchDepth: Int? = null): GitOutput {
        val args = mutableListOf("-c", "protocol.version=2", "fetch")
        if (!refSpec.any { it == refHelper.tagsRefSpec }) {
            args.add("--no-tags")
        }

        args.addAll(listOf("--prune", "--progress", "--no-recurse-submodules"))
        if (fetchDepth != null && fetchDepth > 0) {
            args.add("--depth=$fetchDepth")
        } else if (File(workingDirectory, ".git${File.separator}shallow").exists()) {
            args.add("--unshallow")
        }

        args.add("origin")
        args.addAll(refSpec)

        return retryHelper.execute {
            execGit(args)
        }
    }

    fun getDefaultBranch(repositoryUrl: String): String {
        var output: GitOutput? = null
        retryHelper.execute {
            output = execGit(listOf("ls-remote", "--quiet", "--exit-code", "--symref", repositoryUrl, "HEAD"))
        }

        if (output != null) {
            // Satisfy compiler, will always be set
            val defaultBranch = parseDefaultBranch(output!!.stdout)
            if (defaultBranch != null) {
                return defaultBranch
            }
        }

        logger.info("getDefaultBranch Output Code: ${output!!.stderr}")
        throw Error("Unexpected output when retrieving default branch")
    }

    @TestOnly
    fun parseDefaultBranch(lines: String): String? {
        for (splitLine in lines.trim().split("\n")) {
            val line = splitLine.trim()
            if (line.startsWith("ref:") && line.endsWith("HEAD")) {
                return line
                    .substring("ref:".length, line.length - "HEAD".length)
                    .trim()
            }
        }

        logger.error("Output: $lines")
        logger.error("Unexpected output when retrieving default branch")
        return null
    }

    fun submoduleForeach(command: String, recursive: Boolean): GitOutput {
        val args = mutableListOf("submodule", "foreach")
        if (recursive) {
            args.add("--recursive")
        }
        args.add(command)

        return execGit(args)
    }

    fun submoduleSync(recursive: Boolean): GitOutput {
        val args = mutableListOf("submodule", "sync")
        if (recursive) {
            args.add("--recursive")
        }

        return execGit(args)
    }

    fun submoduleUpdate(fetchDepth: Int, recursive: Boolean): GitOutput {
        val args = mutableListOf("-c", "protocol.version=2")
        args.addAll(listOf("submodule", "update", "--init", "--force"))
        if (fetchDepth > 0) {
            args.add("--depth=$fetchDepth")
        }

        if (recursive) {
            args.add("--recursive")
        }

        return execGit(args)
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

    fun execGit(args: List<String>, allowAllExitCodes: Boolean = false, silent: Boolean = false): GitOutput {
        val result = GitOutput()

        val env = mutableMapOf<String, String>()
        for ((key, value) in System.getenv()) {
            env[key] = value
        }
        for ((key, value) in gitEnv) {
            env[key] = value
        }

        val stdout = mutableListOf<String>()
        val stderr = mutableListOf<String>()
        val options = ExecOptions(
            cwd = workingDirectory,
            env = env,
            silent = silent,
            ignoreReturnCode = allowAllExitCodes,
            listeners = StringListExecListeners(stdout, stderr)
        )

        result.exitCode = exec.exec(gitPath, args, options)
        result.stdout = stdout.joinToString("\n")
        result.stderr = stderr.joinToString("\n")

        return result
    }

    fun pullRemote(remoteName: String, refSpec: String, fetchDepth: Int? = null): GitOutput {
        val args = mutableListOf("pull", "--progress", "--no-recurse-submodules", remoteName, refSpec)
        if (fetchDepth != null && fetchDepth > 0) {
            args.add("--depth=$fetchDepth")
        }

        return execGit(args)
    }

    fun setEnvironmentVariable(name: String, value: String) {
        gitEnv[name] = value
    }

    fun tryConfigUnset(configKey: String, globalConfig: Boolean = false): Boolean {
        val output =
            execGit(listOf("config", if (globalConfig) "--global" else "--local", "--unset-all", configKey), true)
        return output.exitCode == 0
    }

    fun tagExists(ref: String): Boolean {
        val output = execGit(listOf("tag", "--list", ref))
        return output.stdout.trim().isNotEmpty()
    }

    fun removeEnvironmentVariable(name: String) {
        gitEnv.remove(name)
    }
}

data class GitOutput(
    var stdout: String = "",
    var exitCode: Int = 0,
    var stderr: String = ""
)
