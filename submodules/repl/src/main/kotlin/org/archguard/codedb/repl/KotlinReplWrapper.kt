package org.archguard.codedb.repl

import kotlinx.serialization.Serializable
import org.jetbrains.kotlinx.jupyter.EvalRequestData
import org.jetbrains.kotlinx.jupyter.ReplForJupyter
import org.jetbrains.kotlinx.jupyter.api.Code
import org.jetbrains.kotlinx.jupyter.compiler.util.EvaluatedSnippetMetadata
import org.jetbrains.kotlinx.jupyter.libraries.EmptyResolutionInfoProvider
import org.jetbrains.kotlinx.jupyter.libraries.LibraryResolver
import org.jetbrains.kotlinx.jupyter.messaging.NoOpDisplayHandler
import org.jetbrains.kotlinx.jupyter.repl.EvalResult
import org.jetbrains.kotlinx.jupyter.repl.creating.createRepl
import org.slf4j.LoggerFactory
import java.io.File
import kotlin.script.experimental.jvm.util.KotlinJars

class KotlinReplWrapper {
    private val logger = LoggerFactory.getLogger(this.javaClass)
    private val repl: ReplForJupyter

    init {
        this.repl = this.makeEmbeddedRepl()
    }

    private fun makeEmbeddedRepl(): ReplForJupyter {
        val property = System.getProperty("java.class.path")
        var embeddedClasspath: MutableList<File> = property.split(File.pathSeparator).map(::File).toMutableList()

        val isInRuntime = embeddedClasspath.size == 1
        if (isInRuntime) {
            System.setProperty("kotlin.script.classpath", property)

            val compiler = KotlinJars.compilerClasspath
            if (compiler.isNotEmpty()) {
                val tempdir = compiler[0].parent
                embeddedClasspath =
                    File(tempdir).walk(FileWalkDirection.BOTTOM_UP).sortedBy { it.isDirectory }.toMutableList()
            }
        }

        embeddedClasspath = embeddedClasspath.distinctBy { it.name } as MutableList<File>
        logger.info("classpath: $embeddedClasspath")

        return createRepl(
            EmptyResolutionInfoProvider,
            embeddedClasspath,
            libraryResolver = resolveArchGuardDsl(),
            displayHandler = NoOpDisplayHandler,
            isEmbedded = true
        )
    }

    fun eval(code: Code, jupyterId: Int = -1, storeHistory: Boolean = true) =
        repl.eval(EvalRequestData(code, jupyterId, storeHistory)).toWrapperResult()

    companion object {
        fun resolveArchGuardDsl(): LibraryResolver {
            val lib = "archguard" to """
            {
                "imports": [
                    "org.archguard.dsl.*"
                ],
                "init": []
            }
                """.trimIndent()

            return listOf(lib).toLibraries()
        }
    }
}

private fun EvalResult.toWrapperResult(): WrapperReplResult {
    return WrapperReplResult.from(this)
}

class WrapperReplResult(
    val resultValue: Any?,
    val metadata: WrapperEvaluatedSnippetMetadata,
) {

    companion object {
        fun from(result: EvalResult): WrapperReplResult {
            return WrapperReplResult(
                result.resultValue,
                WrapperEvaluatedSnippetMetadata.from(result.metadata),
            )
        }
    }
}

@Serializable
class WrapperEvaluatedSnippetMetadata(
    val newClasspath: List<String> = emptyList(),
    val newSources: List<String> = emptyList(),
    val newImports: List<String> = emptyList(),
    val evaluatedVariablesState: Map<String, String?> = mutableMapOf(),
) {
    companion object {
        fun from(metadata: EvaluatedSnippetMetadata): WrapperEvaluatedSnippetMetadata {
            return WrapperEvaluatedSnippetMetadata(
                metadata.newClasspath,
                metadata.newSources,
                metadata.newImports,
                metadata.evaluatedVariablesState,
            )
        }
    }
}
