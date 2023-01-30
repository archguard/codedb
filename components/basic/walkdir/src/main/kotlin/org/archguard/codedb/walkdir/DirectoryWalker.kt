package org.archguard.codedb.walkdir

import kotlinx.coroutines.channels.Channel
import org.eclipse.jgit.lib.Constants
import org.eclipse.jgit.lib.FileMode
import org.eclipse.jgit.lib.ObjectId
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.revwalk.RevCommit
import org.eclipse.jgit.revwalk.RevTree
import org.eclipse.jgit.revwalk.RevWalk
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import org.eclipse.jgit.treewalk.TreeWalk
import org.eclipse.jgit.treewalk.filter.AndTreeFilter
import org.eclipse.jgit.treewalk.filter.NotIgnoredFilter
import org.eclipse.jgit.treewalk.filter.TreeFilter
import org.slf4j.LoggerFactory
import java.io.File
import java.io.IOException

/**
 * Walks a directory and sends all files to a channel.
 *
 * @param workdir The directory to walk.
 * @param output The channel to send the files to.
 * @param withGitIgnore If true, the walker will use the .gitignore file to filter files.
 *
 * examples:
 * ```kotlin
 * val channel = Channel<File>()
 * runBlocking {
 *     launch {
 *         for (fileJob in channel) {
 *             println("file: $fileJob")
 *             count++
 *         }
 *     }
 *
 *     val walker = DirectoryWalker(rootDir, channel)
 *     walker.start()
 *
 *     channel.close()
 * }
 * ```
 */
class DirectoryWalker(workdir: String, private val output: Channel<File>, private val withGitIgnore: Boolean = false) {
    private val root = File(workdir)

    suspend fun start() {
        if (withGitIgnore) {
            gitWalker()
        } else {
            normalWalker()
        }
    }

    private suspend fun gitWalker() {
        val repository = FileRepositoryBuilder().findGitDir(root).build()
        repository.use { repo ->
            val tree = getTree(repo)
            val treeWalk = TreeWalk(repo)

            treeWalk.addTree(tree)
            treeWalk.isRecursive = false
            treeWalk.filter = TreeFilter.ALL

            while (treeWalk.next()) {
                if (treeWalk.isSubtree) {
                    treeWalk.enterSubtree();
                } else {
                    val path = treeWalk.pathString
                    val file = File(root, path)
                    val fileMode = treeWalk.getFileMode(0)

                    if (fileMode.equals(FileMode.REGULAR_FILE)) {
                        output.send(file)
                    }
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun getTree(repository: Repository): RevTree {
        val lastCommitId: ObjectId = repository.resolve(Constants.HEAD)
        logger.warn("lastCommitId: $lastCommitId")

        RevWalk(repository).use { revWalk ->
            val commit: RevCommit = revWalk.parseCommit(lastCommitId)
            return commit.tree
        }
    }

    private suspend fun normalWalker() {
        root.walk().filter { it.isFile }.forEach {
            output.send(it)
        }
    }

    companion object {
        val logger = LoggerFactory.getLogger(DirectoryWalker::class.java)
    }
}