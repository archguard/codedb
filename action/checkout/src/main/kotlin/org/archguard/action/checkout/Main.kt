package org.archguard.action.checkout

import org.archguard.action.checkout.helper.GitAuthHelper
import org.archguard.action.io.FileExt
import java.io.File


val logger = org.slf4j.LoggerFactory.getLogger("CheckoutAction")

fun main(args: Array<String>) {
    // println args
    logger.info("args: ${args.joinToString(", ")}")
    // args to GitSourceSettings
    val settings = GitSourceSettings.fromArgs(args)

    val git = GitCommandManager(settings.repositoryPath)

    val authHelper = GitAuthHelper(git, settings)

    if (File(settings.repositoryPath).exists()) {
        FileExt.rmdir(settings.repositoryPath)
    }

    FileExt.mkdir(settings.repositoryPath)

    if (settings.serverSide) {
        // todo: config token and ssh key for clone
        authHelper.configureTempGlobalConfig()
    }

    git.config("safe.directory", settings.repositoryPath, true, true)

    logger.info("Initializing git repository")
    git.init()
    git.remoteAdd("origin", settings.repository)

    logger.info("Disabling automatic garbage collection")
    git.tryDisableAutomaticGarbageCollection()

    settings.ref = git.getDefaultBranch(settings.repository)
    logger.info("Determining default branch for repository: ${settings.repository}, default branch: ${settings.ref}")

    val refHelper = RefHelper()
    val refSpec = refHelper.getRefSpecForAllHistory(settings.ref, settings.branch)

    logger.info("Fetching all history for $refSpec")
    git.fetch(refSpec)

    val checkoutInfo = refHelper.getCheckoutInfo(git, settings.ref, settings.commit)
    git.checkout(checkoutInfo.ref, checkoutInfo.startPoint)

    git.submoduleSync(settings.nestedSubmodules)
    git.submoduleUpdate(settings.fetchDepth, settings.nestedSubmodules)
    git.submoduleForeach("git config --local gc.auto 0", settings.nestedSubmodules)

    // clean up
    if (settings.serverSide) {
        // todo: remove auth
        authHelper.removeGlobalConfig()
    }
}
