package org.archguard.runner.registry

import kotlinx.serialization.Serializable

@Serializable
data class ActionRegistry(
    val name: String,
    val description: String,
    val version: String,
    val author: String,
    val time: Time,
    val versions: Map<String, Version>,
    val homepage: String,
    val repository: Repository,
    val license: String,
    val readme: String
) {

}

@Serializable
data class Repository(
    val type: String,
    val url: String
)

@Serializable
data class Time(
    val modified: String,
    val created: String,
    val latestVersion: String
)

@Serializable
data class Version(
    val name: String,
    val description: String,
    val dist: Dist
)

@Serializable
data class Dist(
    val sha: String,
    val pkg: String
)
