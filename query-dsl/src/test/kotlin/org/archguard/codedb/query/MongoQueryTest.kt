package org.archguard.codedb.query

import chapi.domain.core.CodeDataStruct
import com.mongodb.DBCollection
import com.mongodb.DBObject
import com.mongodb.MongoClient
import com.querydsl.mongodb.AbstractMongodbQuery
import com.querydsl.mongodb.morphia.MorphiaSerializer
import dev.morphia.Datastore
import dev.morphia.Morphia
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.jupiter.api.Test
import java.util.function.Function


internal class MongoQueryTest {
    @MockK
    lateinit var mongo: MongoClient

    @Before
    fun setUp() = MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks

    @Test
    internal fun sample() {
        val datastore = Morphia.createDatastore("chapi")
        val ds = CodeDataStruct("user")

        val query: CodeMorphiaQuery<CodeDataStruct> = CodeMorphiaQuery(datastore, CodeDataStruct::class.java)
//        val list: List<CodeDataStruct> = query
//            .where(ds.NodeName.eq("Bob"))
//            .fetch()
    }
}


public class CodeMorphiaQuery<K>(val datastore: Datastore, entityType: Class<out K?>?) {

}
