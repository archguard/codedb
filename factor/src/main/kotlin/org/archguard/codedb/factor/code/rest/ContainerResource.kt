package org.archguard.codedb.factor.code.rest

import kotlinx.serialization.Serializable
import javax.persistence.Entity
import javax.persistence.Id

@Entity
@Serializable
class ContainerResource(
    @Id
    val id: String,
    val systemId: String,
    var sourceUrl: String,
    var originUrl: String = "",
    val sourceHttpMethod: String,
    val packageName: String,
    val className: String,
    val methodName: String
) {
    constructor() : this("", "", "", "", "", "", "", "")
}

@Entity
@Serializable
class ContainerDemand(
    @Id
    val id: String,
    val systemId: String,
    val sourcePackage: String,
    val sourceClass: String,
    val sourceMethod: String,
    var targetUrl: String,
    var originUrl: String = "",
    val targetHttpMethod: String,
) {
    constructor() : this("", "", "", "", "", "", "", "")
}