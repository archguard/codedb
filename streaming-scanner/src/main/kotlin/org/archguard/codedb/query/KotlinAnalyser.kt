package org.archguard.codedb.query

import chapi.domain.core.CodeDataStruct
import chapi.parser.ParseMode
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import java.io.File
import kotlin.streams.asStream

class KotlinAnalyser(val path: String) {
    private val impl = chapi.ast.kotlinast.KotlinAnalyser()

    fun analyse(): List<CodeDataStruct> = runBlocking {
        val basepath = File(path)
        getFilesByPath(path) {
            it.absolutePath.endsWith(".kt") || it.absolutePath.endsWith(".kts")
        }
            .map { async { analysisByFile(it, basepath) } }.awaitAll()
            .flatten()
//            .also { client.saveDataStructure(it) }
    }

    private fun analysisByFile(file: File, basepath: File): List<CodeDataStruct> {
        return impl.analysis(file.readContent(), file.name, ParseMode.Full).DataStructures
            .map {
                it.apply {
                    it.FilePath = file.relativeTo(basepath).toString()
                }
            }
    }

    fun getFilesByPath(path: String, predicate: (File) -> Boolean = { true }): List<File> {
        return File(path).walk().asStream().parallel()
            .filter { it.isFile }
            .filter { predicate(it) }
            .toList()
    }

    fun File.readContent(): String {
        val text = readText()
        // fix for Window issue
        if (text.startsWith("\uFEFF")) {
            return text.substring(1);
        }
        return text
    }
}
