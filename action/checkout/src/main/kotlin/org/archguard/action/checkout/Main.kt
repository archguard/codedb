package org.archguard.action.checkout

import org.archguard.action.checkout.helper.GitAuthHelper

fun main(args: Array<String>) {
    // println args
    println("args: ${args.joinToString(", ")}")
    // args to GitSourceSettings
    val sourceSettings = GitSourceSettings.fromArgs(args)

    val git = GitCommandManager()

    val authHelper = GitAuthHelper(git, sourceSettings)

    if (sourceSettings.serverSide) {
        authHelper.configureTempGlobalConfig()
        // todo: need to support ssh key
        authHelper.configureAuth()
    }

    git.config("safe.directory", sourceSettings.repositoryPath, true, true)
}
