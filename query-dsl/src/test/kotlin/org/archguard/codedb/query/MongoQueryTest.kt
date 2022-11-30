package org.archguard.codedb.query

import chapi.domain.core.CodeDataStruct
import com.mongodb.MongoClient
import com.querydsl.core.types.Ops
import com.querydsl.core.types.Predicate
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.mongodb.morphia.MorphiaQuery
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.mongodb.morphia.Morphia


internal class MongoQueryTest {
    @Test
    @Disabled
    internal fun sample() {
        val mongo = MongoClient()
        val morphia = Morphia().map(CodeDataStruct::class.java)
        val datastore = morphia.createDatastore(mongo, "codedb")

        val query: MorphiaQuery<CodeDataStruct> = MorphiaQuery(morphia, datastore, CodeDataStruct::class.java)
        val list: List<CodeDataStruct> = query
            .where(CodeDataStruct().NodeName.eq("Bob"))
            .fetch()
    }
}

fun String.eq(string: String): Predicate {
    return Expressions.booleanOperation(Ops.EQ, Expressions.stringPath(this), Expressions.stringPath(string))
}
