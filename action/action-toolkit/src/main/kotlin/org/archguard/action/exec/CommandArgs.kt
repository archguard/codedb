package org.archguard.action.exec

object CommandArgs {
    fun argsToMap(args: Array<String>): Map<String, String> {
        return args.toList().chunked(2).associate { it[0] to it[1] }
    }
}