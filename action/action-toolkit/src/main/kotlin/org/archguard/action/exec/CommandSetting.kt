package org.archguard.action.exec

interface CommandSetting {
    companion object fromArgs {
        fun fromArgs(args: Array<String>): CommandSetting {
            return object : CommandSetting {}
        }
    }
}