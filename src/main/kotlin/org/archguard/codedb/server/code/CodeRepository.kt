package org.archguard.codedb.server.code

import org.archguard.codedb.server.code.domain.CodeDocument
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import reactor.core.publisher.Mono


interface CodeRepository : ReactiveMongoRepository<CodeDocument, String>, QuerydslPredicateExecutor<CodeDocument> {
    fun deleteAllBySystemId(systemId: String): Mono<Void>
}
