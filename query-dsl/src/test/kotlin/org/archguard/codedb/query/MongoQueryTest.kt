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

class CodeDocument(
    val id: String = "",
    val systemId: String = "",
    val language: String = "",
    val path: String = "",

    // data_struct
    val ds: CodeDataStruct = CodeDataStruct(),
    val ds_package: String = "",
    val ds_node_name: String = "",
    val ds_file_path: String = "",
) {

}


internal class MongoQueryTest {
    @Test
//    @Disabled
    internal fun sample() {
        val mongo = MongoClient()
        val morphia = Morphia().map(CodeDocument::class.java)
        val datastore = morphia.createDatastore(mongo, "codedb")

        val query: MorphiaQuery<CodeDocument> = MorphiaQuery(morphia, datastore, CodeDocument::class.java)
        val list: List<CodeDocument> = query
            .where(CodeDocument().systemId.eq("Bob"))
            .fetch()
    }
}

fun String.eq(string: String): Predicate {
    return Expressions.booleanOperation(Ops.EQ, Expressions.stringPath(this), Expressions.stringPath(string))
}
