package org.archguard.codedb.query.mongo

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.reactivestreams.client.MongoClients
import org.junit.jupiter.api.Test

class NativeMongoTest {

    @Test
    fun testQuery() {
//        val connString = ConnectionString("mongodb://localhost:27017/test?w=majority")
//        val settings = MongoClientSettings.builder()
//            .applyConnectionString(connString)
//            .retryWrites(true)
//            .build()
//        val mongoClient = MongoClients.create(settings)
//        val database = mongoClient.getDatabase("codedb")
//        val collection = database.getCollection("codeDocument")
//
//        NativeMongo().query(collection)
    }
}