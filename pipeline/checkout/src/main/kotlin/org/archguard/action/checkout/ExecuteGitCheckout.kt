package org.archguard.action.checkout

import org.archguard.action.checkout.helper.GitAuthHelper
import org.archguard.action.checkout.helper.RefHelper
import org.archguard.action.io.FileExt
import java.io.File

fun executeGitCheckout(settings: GitSourceSettings) {
    val git = GitCommandManager(settings.workdir + File.separator + settings.repositoryPath)

    doCheckout(git, settings)
}

fun doCheckout(git: GitCommandManager, settings: GitSourceSettings) {
    val authHelper = GitAuthHelper(git, settings)

    if (File(git.workingDirectory).exists()) {
        FileExt.rmdir(git.workingDirectory)
    }

    FileExt.mkdir(git.workingDirectory)

    authHelper.configureTempGlobalConfig()

    git.config("safe.directory", git.workingDirectory, true, true)

    logger.info("Initializing git repository")
    git.init()
    git.remoteAdd("origin", settings.repository)

    if (settings.authToken.isNotEmpty()) {
        logger.info("Configuring auth")
        authHelper.configureAuth()
    }

    logger.info("Disabling automatic garbage collection")
    git.tryDisableAutomaticGarbageCollection()

    settings.ref = git.getDefaultBranch(git.workingDirectory)
    logger.info("Determining default branch for repository: ${git.workingDirectory}, default branch: ${settings.ref}")

    val refHelper = RefHelper()
    val refSpec = refHelper.getRefSpecForAllHistory(settings.ref, settings.branch)

    logger.info("Fetching all history for {}", refSpec)
    git.fetch(refSpec)

    val checkoutInfo = refHelper.getCheckoutInfo(git, settings.ref, settings.commit)
    git.checkout(checkoutInfo.ref, checkoutInfo.startPoint)

    // submodules
    if (settings.submodule) {
        git.submoduleSync(settings.nestedSubmodules)
        git.submoduleUpdate(settings.fetchDepth, settings.nestedSubmodules)
        git.submoduleForeach("git config --local gc.auto 0", settings.nestedSubmodules)
    }

    // clean up
    authHelper.removeGlobalConfig()
}