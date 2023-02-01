package org.archguard.codedb.server

import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import org.archguard.codedb.server.code.CodeRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.convert.MappingMongoConverter
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories


@Configuration
@EnableReactiveMongoRepositories(
    basePackageClasses = [CodeRepository::class]
)
open class MongoConfig : AbstractReactiveMongoConfiguration() {

    override fun getDatabaseName() = "codedb"

    override fun reactiveMongoClient(): MongoClient = mongoClient()

    @Bean
    open fun mongoClient(): MongoClient = MongoClients.create()

    @Bean
    override fun reactiveMongoTemplate(
        databaseFactory: ReactiveMongoDatabaseFactory,
        mongoConverter: MappingMongoConverter
    ): ReactiveMongoTemplate = ReactiveMongoTemplate(mongoClient(), databaseName)
}