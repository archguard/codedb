package org.archguard.codedb.core

interface Project {
    var name: String

    var displayName: String

    var description: String

    var workdir: String

    var basedir: String

    var subProjects: List<Project>
}