package org.archguard.codedb.git

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.transport.CredentialsProvider
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider
import org.slf4j.LoggerFactory
import java.io.File
import java.nio.file.Path

private const val DEFAULT_BRANCH = "master"

class GitCommand {
    fun clone(url: String, depth: Int = 1): Git? {
        return Git.cloneRepository()
            .setURI(url)
            .call()
    }

    fun clone(url: String, target: File): Git? {
        return Git.cloneRepository()
            .setURI(url)
            .setDirectory(target)
            .call()
    }

    fun clone(url: String, target: File, branch: String = DEFAULT_BRANCH): Git? {
        return Git.cloneRepository()
            .setURI(url)
            .setDirectory(target)
            .setBranch(branch)
            .call()
    }

    fun clone(url: String, target: File, branch: String = DEFAULT_BRANCH, username: String, password: String): Git? {
        return Git.cloneRepository()
            .setURI(url)
            .setDirectory(target)
            .setBranch(branch)
            .setCredentialsProvider(UsernamePasswordCredentialsProvider(username, password))
            .call()
    }

    fun clone(
        url: String,
        target: File,
        branch: String = DEFAULT_BRANCH,
        credentialsProvider: CredentialsProvider
    ): Git? {
        return Git.cloneRepository()
            .setURI(url)
            .setDirectory(target)
            .setBranch(branch)
            .setCredentialsProvider(credentialsProvider)
            .call()
    }

    fun pull(git: Git) {
        try {
            git.clean().call()
            git.pull().call()
        } catch (e: Exception) {
            logger.error("git pull failed: ", e)
        }
    }

    companion object {
        var logger = LoggerFactory.getLogger(GitCommand::class.java)
    }
}