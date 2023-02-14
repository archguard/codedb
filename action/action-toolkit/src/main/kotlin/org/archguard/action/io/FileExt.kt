package org.archguard.action.io

import java.io.File

object FileExt {
    fun rmdir(dir: String) {
        val file = File(dir)
        if (file.isDirectory && file.exists()) {
            file.deleteRecursively()
        }
    }

    fun mkdir(dir: String) {
        val file = File(dir)
        if (!file.exists()) {
            file.mkdirs()
        }
    }
}