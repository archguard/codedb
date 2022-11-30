package org.archguard.codedb.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.mongo.MongoReactiveDataAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [MongoReactiveDataAutoConfiguration::class])
open class CodedbApplication

fun main(args: Array<String>) {
    runApplication<CodedbApplication>(*args)
}
