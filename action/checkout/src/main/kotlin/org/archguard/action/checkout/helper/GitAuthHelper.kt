package org.archguard.action.checkout.helper

import Shell
import org.archguard.action.checkout.GitCommandManager
import org.archguard.action.checkout.GitSourceSettings
import org.archguard.action.exec.Command
import org.slf4j.LoggerFactory
import java.io.File
import java.io.IOException
import java.lang.System.getProperty
import java.lang.System.getenv
import java.net.URL
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.*

const val SSH_COMMAND_KEY = "core.sshCommand"
val IS_WINDOWS: Boolean = getProperty("os.name").startsWith("Windows")

class GitAuthHelper(
    val git: GitCommandManager,
    val settings: GitSourceSettings
) {
    private var tokenConfigKey = ""
    private var tokenConfigValue = ""
    private var tokenPlaceholderConfigValue = ""
    private var insteadOfKey = ""
    private val insteadOfValues = mutableListOf<String>()

    private var sshCommand: String = ""
    private var sshKeyPath: String = ""
    private var sshKnownHostsPath: String = ""
    private var temporaryHomePath: String = ""


    init {
        // Token auth header
        val serverUrl = URL(settings.gitServerUrl)
        tokenConfigKey = "http.${serverUrl.origin()}/.extraheader" // "origin" is SCHEME://HOSTNAME[:PORT]

        val basicCredential = Base64.getEncoder().encodeToString("x-access-token:${settings.authToken}".toByteArray())
        tokenPlaceholderConfigValue = "AUTHORIZATION: basic ***"
        tokenConfigValue = "AUTHORIZATION: basic $basicCredential"

        // Instead of SSH URL
        insteadOfKey = "url.${serverUrl.origin()}/.insteadOf" // "origin" is SCHEME://HOSTNAME[:PORT]
        insteadOfValues.add("git@${serverUrl.host}:")
    }

    fun configureTempGlobalConfig(): String {
        // Already setup global config
        if (temporaryHomePath.isNotEmpty()) {
            return "$temporaryHomePath${File.separator}.gitconfig"
        }

        // Create a temp home directory
        val runnerTemp = getenv("RUNNER_TEMP") ?: ""
        assert(runnerTemp.isNotEmpty()) { "RUNNER_TEMP is not defined" }

        val uniqueId = UUID.randomUUID().toString()
        temporaryHomePath = "$runnerTemp/$uniqueId"
        File(temporaryHomePath).mkdirs()

        // Copy the global git config
        val gitConfigPath = "${getenv("HOME") ?: getProperty("user.home")}/.gitconfig"
        val newGitConfigPath = "$temporaryHomePath/.gitconfig"
        val configExists = File(gitConfigPath).exists()
        if (configExists) {
            logger.info("Copying '$gitConfigPath' to '$newGitConfigPath'")
            Files.copy(Paths.get(gitConfigPath), Paths.get(newGitConfigPath), StandardCopyOption.REPLACE_EXISTING)
        } else {
            File(newGitConfigPath).writeText("")
        }

        // Override HOME
        logger.info("Temporarily overriding HOME='$temporaryHomePath' before making global git config changes")
        git.setEnvironmentVariable("HOME", temporaryHomePath)

        return newGitConfigPath
    }

    fun removeGlobalConfig() {
        if (temporaryHomePath.isNotEmpty()) {
            logger.info("Unsetting HOME override")
            git.removeEnvironmentVariable("HOME")
            File(temporaryHomePath).deleteRecursively()
        }
    }

    fun configureAuth() {
        removeSsh()
        removeToken()

        configureSsh()
        configureToken()
    }

    fun configureSsh() {
        if (settings.sshKey.isEmpty()) {
            return
        }

        // Write key
        val runnerTemp = getenv("RUNNER_TEMP") ?: ""
        assert(runnerTemp.isNotEmpty()) { "RUNNER_TEMP is not defined" }
        val uniqueId = UUID.randomUUID().toString()
        sshKeyPath = Paths.get(runnerTemp, uniqueId).toString()

//        stateHelper.setSshKeyPath(sshKeyPath)

        File(runnerTemp).mkdirs()
        File(sshKeyPath).writeText(settings.sshKey.trim() + "\n")

        // Remove inherited permissions on Windows
        if (IS_WINDOWS) {
            val icacls = Shell.which("icacls.exe")
            Command.exec("${icacls} \"$sshKeyPath\" /grant:r \"${getenv("USERDOMAIN")}\\${getenv("USERNAME")}:F\"")
            Command.exec("${icacls} \"$sshKeyPath\" /inheritance:r")
        }

        // Write known hosts
        val userKnownHostsPath = Paths.get(System.getProperty("user.home"), ".ssh", "known_hosts").toString()
        val userKnownHosts = try {
            File(userKnownHostsPath).readText()
        } catch (err: Exception) {
            if (err is IOException || err is SecurityException) {
                throw err
            } else {
                ""
            }
        }

        var knownHosts = ""
        if (userKnownHosts.isNotEmpty()) {
            knownHosts += "# Begin from $userKnownHostsPath \n$userKnownHosts \n# End from $userKnownHostsPath \n"
        }
        if (settings.sshKnownHosts.isNotEmpty()) {
            knownHosts += "# Begin from input known hosts \n${settings.sshKnownHosts} \n# end from input known hosts \n"
        }
        knownHosts += "# Begin implicitly added github.com \ngithub.com ssh-rsa AAAAB3NzaC1yc2EAAAABIwAAAQEAq2A7hRGmdnm9tUDbO9IDSwBK6TbQa+PXYPCPy6rbTrTtw7PHkccKrpp0yVhp5HdEIcKr6pLlVDBfOLX9QUsyCOV0wzfjIJNlGEYsdlLJizHhbn2mUjvSAHQqZETYP81eFzLQNnPHt4EVVUh7VfDESU84KezmD5QlWpXLmvU31/yMf+Se8xhHTvKSCZIFImWwoG6mbUoWf9nzpIoaSjB+weqqUUmpaaasXVal72J+UX2B+2RPW3RcT0eOzQgqlJL3RKrTJvdsjE3JEAvGq3lGHSZXy28G3skua2SmVi/w4yCE6gbODqnTWlg7+wC604ydGXA8VJiS5ap43JXiUFFAaQ== \n# End implicitly added github.com \n"
        sshKnownHostsPath = Paths.get(runnerTemp, "$uniqueId-known_hosts").toString()
//        stateHelper.setSshKnownHostsPath(sshKnownHostsPath)
        File(sshKnownHostsPath).writeText(knownHosts)

        // Configure GIT_SSH_COMMAND
        val sshPath = Shell.which("ssh")
        sshCommand = "\"$sshPath\" -i \"$sshKeyPath\""
        if (settings.sshStrict) {
            sshCommand += " -o StrictHostKeyChecking=yes -o CheckHostIP=no"
        }
        sshCommand += " -o \"UserKnownHostsFile=$sshKnownHostsPath\""
        logger.info("Temporarily overriding GIT_SSH_COMMAND=$sshCommand")
        git.setEnvironmentVariable("GIT_SSH_COMMAND", sshCommand)

        // Configure core.sshCommand
        if (settings.persistCredentials) {
            git.config(SSH_COMMAND_KEY, sshCommand)
        }
    }

    fun configureToken(configPath: String? = null, globalConfig: Boolean = false) {
        // Validate args
        assert((configPath != null && globalConfig) || (configPath == null && !globalConfig)) { "Unexpected configureToken parameter combinations" }

        var defaultConfigPath = configPath
        // Default config path
        if (configPath == null && !globalConfig) {
            defaultConfigPath = Paths.get(git.workingDirectory, ".git", "config").toString()
        }

        // Configure a placeholder value. This approach avoids the credential being captured
        // by process creation audit events, which are commonly logged. For more information,
        // refer to https://docs.microsoft.com/en-us/windows-server/identity/ad-ds/manage/component-updates/command-line-process-auditing
        git.config(tokenConfigKey, tokenPlaceholderConfigValue, globalConfig)

        // Replace the placeholder
        replaceTokenPlaceholder(defaultConfigPath ?: "")
    }

    fun replaceTokenPlaceholder(configPath: String) {
        // Read config file
        val content = File(configPath).readText()
        val placeholderIndex = content.indexOf(tokenPlaceholderConfigValue)
        if (placeholderIndex < 0 || placeholderIndex != content.lastIndexOf(tokenPlaceholderConfigValue)) {
            throw Error("Unable to replace auth placeholder in $configPath")
        }
        val contentReplaced = content.replace(tokenPlaceholderConfigValue, tokenConfigValue)
        File(configPath).writeText(contentReplaced)
    }

    fun removeSsh() {
        // SSH key
        val keyPath = sshKeyPath
        if (keyPath.isNotEmpty()) {
            try {
                File(keyPath).deleteRecursively()
            } catch (err: Exception) {
                logger.info(err.message)
                logger.info("Failed to remove SSH key '$keyPath'")
            }
        }

        // SSH known hosts
        val knownHostsPath = sshKnownHostsPath
        if (knownHostsPath.isNotEmpty()) {
            try {
                File(knownHostsPath).deleteRecursively()
            } catch (err: Exception) {
                // Intentionally empty
            }
        }

        // SSH command
        removeGitConfig(SSH_COMMAND_KEY)
    }

    fun removeToken() {
        removeGitConfig(this.tokenConfigKey)
    }

    private fun removeGitConfig(configKey: String, submoduleOnly: Boolean = false) {
        if (!submoduleOnly) {
            if (git.configExists(configKey) && !git.tryConfigUnset(configKey)) {
                // Load the config contents
                logger.info("Failed to remove '$configKey' from the git config")
            }
        }

        val pattern = configKey.replace(Regex("[.*+?^${'$'}()|\\[\\]\\\\]"), "\\$0")
        git.submoduleForeach(
            // wrap the pipeline in quotes to make sure it's handled properly by submoduleForeach, rather than just the first part of the pipeline
            "sh -c \"git config --local --name-only --get-regexp '$pattern' && git config --local --unset-all '$configKey' || :\"",
            true
        )
    }

    companion object {
        val logger = LoggerFactory.getLogger(GitAuthHelper::class.java)
    }
}

private fun URL.origin(): String {
    val port = if (port == -1) "" else ":$port"
    return "$protocol://$host$port"
}
