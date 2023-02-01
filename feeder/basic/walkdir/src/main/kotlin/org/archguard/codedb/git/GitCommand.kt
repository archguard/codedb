package org.archguard.codedb.git

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.transport.CredentialsProvider
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider
import java.io.File

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
}