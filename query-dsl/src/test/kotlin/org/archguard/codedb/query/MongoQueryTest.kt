package org.archguard.codedb.query

import chapi.domain.core.CodeDataStruct
import com.mongodb.MongoClient
import com.querydsl.mongodb.morphia.MorphiaQuery
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Disabled
import org.mongodb.morphia.Morphia


internal class MongoQueryTest {
    @Test
    @Disabled
    internal fun sample() {
        val mongo = MongoClient()
        val morphia = Morphia().map(CodeDataStruct::class.java)
        val datastore = morphia.createDatastore(mongo, "chapi")

        val ds = CodeDataStruct("user")

        val query: MorphiaQuery<CodeDataStruct> =
            MorphiaQuery<CodeDataStruct>(morphia, datastore, CodeDataStruct::class.java)
//        val list: List<CodeDataStruct> = query
//            .where(ds.NodeName.eq("Bob"))
//            .fetch()

    }
}