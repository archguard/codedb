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
import java.io.File
import java.io.IOException

class DirectoryWalker(val workdir: String, val output: Channel<File>, val withGitIgnore: Boolean = false) {
    val root = File(workdir)

    public final suspend fun start() {
        if (withGitIgnore) {
            gitWalker()
        } else {
            normalWalker()
        }
    }

    private suspend fun gitWalker() {
        val repository = FileRepositoryBuilder().findGitDir(root).build()
        repository.use { _ ->
            val tree = getTree(repository)
            val treeWalk = TreeWalk(repository)
            treeWalk.addTree(tree)
            treeWalk.isRecursive = false
            // todo: update ignore
//            treeWalk.filter = PathFilter.create(".gitignore")

            while (treeWalk.next()) {
                val path = treeWalk.pathString
                val file = File(root, path)
                val fileMode = treeWalk.getFileMode(0)

                if (fileMode.equals(FileMode.REGULAR_FILE)) {
                    output.send(file)
                }
            }
        }
    }


    @Throws(IOException::class)
    private fun getTree(repository: Repository): RevTree {
        val lastCommitId: ObjectId = repository.resolve(Constants.HEAD)
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
}