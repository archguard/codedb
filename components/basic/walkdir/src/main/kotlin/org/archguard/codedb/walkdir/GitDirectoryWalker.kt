package org.archguard.codedb.walkdir

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.archguard.codedb.core.io.DirectoryWalker
import org.eclipse.jgit.lib.Constants
import org.eclipse.jgit.lib.FileMode
import org.eclipse.jgit.lib.ObjectId
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.revwalk.RevCommit
import org.eclipse.jgit.revwalk.RevTree
import org.eclipse.jgit.revwalk.RevWalk
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import org.eclipse.jgit.treewalk.TreeWalk
import org.eclipse.jgit.treewalk.filter.TreeFilter
import org.slf4j.Logger
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
class GitDirectoryWalker(workdir: String): DirectoryWalker(workdir) {
    /**
     * Walks the directory and returns all files.
     */
    override fun start(): List<File> {
        val files: MutableList<File> = mutableListOf()
        runBlocking {
            val channel = Channel<File>()
            launch {
                for (fileJob in channel) {
                    files += fileJob
                }
            }

            start(channel)

            channel.close()
        }

        return files
    }

    /**
     * Walks the directory and sends all files to the channel.
     */
    override suspend fun start(output: Channel<File>) {
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

    companion object {
        val logger: Logger = LoggerFactory.getLogger(GitDirectoryWalker::class.java)
    }
}