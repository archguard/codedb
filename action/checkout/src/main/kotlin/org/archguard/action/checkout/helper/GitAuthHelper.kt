package org.archguard.action.checkout.helper

import org.archguard.action.checkout.GitCommandManager
import org.archguard.action.checkout.GitSourceSettings
import java.io.File
import java.lang.System.*
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
    private val tokenConfigKey = ""
    private val tokenConfigValue = ""
    private val tokenPlaceholderConfigValue = ""
    private val insteadOfKey = ""
    private val insteadOfValues = mutableListOf<String>()

    private var sshCommand: String = ""
    private var sshKeyPath: String = ""
    private var sshKnownHostsPath: String = ""
    private var temporaryHomePath: String = ""

    init {

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
            println("Copying '$gitConfigPath' to '$newGitConfigPath'")
            Files.copy(Paths.get(gitConfigPath), Paths.get(newGitConfigPath), StandardCopyOption.REPLACE_EXISTING)
        } else {
            File(newGitConfigPath).writeText("")
        }

        // Override HOME
        println("Temporarily overriding HOME='$temporaryHomePath' before making global git config changes")
        git.setEnvironmentVariable("HOME", temporaryHomePath)

        return newGitConfigPath
    }

    fun removeGlobalConfig() {
        if (temporaryHomePath.isNotEmpty()) {
            println("Unsetting HOME override")
            git.removeEnvironmentVariable("HOME")
            File(temporaryHomePath).deleteRecursively()
        }
    }

    fun configureAuth() {
        removeSsh()
        removeToken()

//        configureSsh()
//        configureToken()
    }

    fun configureSsh() {
        if (settings.sshKey.isEmpty()) {
            return
        }
    }

    fun removeSsh() {
        // SSH key
        val keyPath = sshKeyPath
        if (keyPath.isNotEmpty()) {
            try {
                File(keyPath).deleteRecursively()
            } catch (err: Exception) {
                println(err.message)
                println("Failed to remove SSH key '$keyPath'")
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
                println("Failed to remove '$configKey' from the git config")
            }
        }

        val pattern = configKey.replace(Regex("[.*+?^${'$'}()|\\[\\]\\\\]"), "\\$0")
        git.submoduleForeach(
            // wrap the pipeline in quotes to make sure it's handled properly by submoduleForeach, rather than just the first part of the pipeline
            "sh -c \"git config --local --name-only --get-regexp '$pattern' && git config --local --unset-all '$configKey' || :\"",
            true
        )
    }
}