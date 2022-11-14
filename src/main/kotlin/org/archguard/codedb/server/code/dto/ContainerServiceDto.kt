package org.archguard.codedb.server.code.dto

import kotlinx.serialization.Serializable
import org.archguard.codedb.server.code.domain.ContainerDemand
import org.archguard.codedb.server.code.domain.ContainerSupply

@Serializable
data class ContainerServiceDto(
    var name: String = "",
    var demands: List<ContainerDemand> = listOf(),
    var resources: List<ContainerSupply> = listOf()
)