package org.archguard.codedb.factor.code.dependency

import kotlinx.serialization.Serializable
import javax.persistence.Entity
import javax.persistence.Id

@Serializable
@Entity
data class PackageDependency(
    @Id
    val id: String,
    val name: String,
    val path: String,
    val version: String,
    val parentId: String,
    val packageManager: String,
    val depName: String,
    val depGroup: String,
    val depArtifact: String,
    val depMetadata: String = "",
    val depSource: String = "",
    val depScope: String,
    val depVersion: String
) {
    constructor() : this("", "", "", "", "", "", "", "", "", "", "", "", "")
}
