package org.archguard.action.checkout

fun main(args: Array<String>) {
    // println args
    println("args: ${args.joinToString(", ")}")
    // args to GitSourceSettings
    GitSourceSettings.fromArgs(args)
}
