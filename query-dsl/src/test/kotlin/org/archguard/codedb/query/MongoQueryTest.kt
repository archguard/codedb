package org.archguard.codedb.query

import chapi.domain.core.CodeDataStruct
import com.mongodb.MongoClient
import com.querydsl.core.types.ConstantImpl
import com.querydsl.core.types.Ops
import com.querydsl.core.types.PathMetadata
import com.querydsl.core.types.PathMetadataFactory
import com.querydsl.core.types.dsl.EntityPathBase
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.core.types.dsl.PathInits
import com.querydsl.core.types.dsl.StringPath
import com.querydsl.mongodb.morphia.MorphiaQuery
import org.bson.types.ObjectId
import org.junit.jupiter.api.Test
import org.mongodb.morphia.Morphia
import org.mongodb.morphia.annotations.Entity
import org.mongodb.morphia.annotations.Id

@Entity("codeDocument")
class CodeDocument(
    @Id
    val _id: ObjectId? = null,
    val systemId: String = "",
    val language: String = "",
    val path: String = "",

    val ds: CodeDataStruct? = null,
    val ds_package: String = "",
    val ds_node_name: String = "",
    val ds_file_path: String = "",
    val _class: String = "",
)

class QCodeDocument : EntityPathBase<CodeDocument> {
    val systemId: StringPath = createString("systemId")

    constructor(variable: String?) : this(
        CodeDocument::class.java,
        PathMetadataFactory.forVariable(variable),
        PathInits.DIRECT2
    )

    constructor(type: Class<out CodeDocument>?, metadata: PathMetadata?) : super(type, metadata)

    constructor(type: Class<out CodeDocument>?, metadata: PathMetadata?, inits: PathInits) : super(
        type,
        metadata,
        inits
    )
}

internal class MongoQueryTest {
    @Test
//    @Disabled
    internal fun sample() {
        val mongo = MongoClient()

        val instance = Morphia()

        val morphia = instance.map(CodeDocument::class.java)
        val datastore = morphia.createDatastore(mongo, "codedb")

        val query: MorphiaQuery<CodeDocument> = MorphiaQuery(morphia, datastore, CodeDocument::class.java)

        val where = query
            .where(
                Expressions.booleanOperation(
                    Ops.EQ,
                    Expressions.stringPath("language"),
                    ConstantImpl.create("kotlin")
                )
            )

        // where.toString() shouldBe { "language" : "kotlin"}
        println(where.toString())

        val documents: List<CodeDocument> = where
            .fetch()

        println(documents)
    }
}
