package org.archguard.codedb.server.code

import chapi.domain.core.CodeDataStruct
import org.archguard.codedb.server.code.domain.CodeDocument
import org.archguard.codedb.server.code.domain.ContainerService
import org.archguard.codedb.server.code.dto.ContainerServiceDto
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.util.*
import java.util.stream.Collectors

@RestController
@RequestMapping("/api/scanner/{systemId}/reporting")
class CodeController(val repository: CodeRepository, private val container: ContainerRepository) {

    // todo: modify to streaming
    @PostMapping(value = ["/class-items"])
    fun save(
        @PathVariable systemId: String,
        @RequestParam language: String,
        @RequestParam path: String,
        @RequestBody inputs: List<CodeDataStruct>,
    ): Mono<Void> {
        val collect = inputs.stream().map { input ->
            CodeDocument(
                UUID.randomUUID().toString(),
                systemId,
                language,
                path,
                input,
                input.Package,
                input.NodeName,
                input.FilePath,
            )
        }.collect(Collectors.toList())

        return repository.deleteAllBySystemId(systemId)
            .thenMany(repository.saveAll(collect))
            .then()
    }

//    // clean up repository, so that we handle for suspend collection
//    @DeleteMapping(value = ["/class-items"])
//    suspend fun delete(@PathVariable systemId: String): Void? {
//        return repository.deleteAllBySystemId(systemId).awaitFirstOrNull()
//    }

    @PostMapping("/container-services")
    fun saveContainerServices(
        @PathVariable systemId: String,
        @RequestParam language: String,
        @RequestParam path: String,
        @RequestBody inputs: List<ContainerServiceDto>,
    ): Mono<Void> {
        val collect = inputs.stream().map { input ->
            ContainerService(
                UUID.randomUUID().toString(),
                systemId,
                language,
                path,
                input.name,
                input.demands,
                input.resources
            )
        }.collect(Collectors.toList())

        return container.deleteAllBySystemId(systemId)
            .thenMany(container.saveAll(collect))
            .then()
    }
}