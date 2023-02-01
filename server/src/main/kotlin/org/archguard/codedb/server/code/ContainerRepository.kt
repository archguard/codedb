package org.archguard.codedb.server.code

import org.archguard.codedb.server.code.domain.ContainerService
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono


interface ContainerRepository : ReactiveMongoRepository<ContainerService, String> {
    fun deleteAllBySystemId(systemId: String): Mono<Void>
}