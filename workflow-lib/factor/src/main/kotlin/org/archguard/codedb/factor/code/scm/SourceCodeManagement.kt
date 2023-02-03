package org.archguard.codedb.fitness.slice.scm

abstract class SourceCodeManagement {
    abstract fun getCommitHistory(): List<Commit>

    abstract fun getBranches(): List<Branch>

    abstract fun getTags(): List<Tag>

    abstract fun getCloc(): Cloc
}

// todo: merge with analyser_git
class Cloc {
    val language: String = ""
    val blank: Int = 0
    val comment: Int = 0
    val code: Int = 0
}

class Tag {
    val name: String = ""
    val date: String = ""
}

class Branch {
    val name: String = ""
    val date: String = ""
}

class Commit {
    val author: String = ""
    val date: String = ""
    val message: String = ""
}
