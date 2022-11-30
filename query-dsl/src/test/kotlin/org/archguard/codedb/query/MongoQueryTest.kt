package org.archguard.codedb.query

import chapi.domain.core.CodeDataStruct
import com.mongodb.MongoClient
import com.querydsl.core.types.PathMetadata
import com.querydsl.core.types.dsl.ComparablePath
import com.querydsl.core.types.dsl.EntityPathBase
import com.querydsl.mongodb.morphia.MorphiaQuery
import org.bson.types.ObjectId
import org.junit.jupiter.api.Test
import org.mongodb.morphia.Morphia
import org.mongodb.morphia.annotations.Entity
import org.mongodb.morphia.annotations.Id

@Entity
class CodeDocument(
    @Id
    val id: ObjectId? = null,
    val systemId: String = "",
    val language: String = "",
    val path: String = "",

    // data_struct
    val ds: CodeDataStruct = CodeDataStruct(),
    val ds_package: String = "",
    val ds_node_name: String = "",
    val ds_file_path: String = "",
)

class QCodeDocument : EntityPathBase<CodeDocument> {
    val id: ComparablePath<ObjectId> = createComparable("id", ObjectId::class.java)

    constructor(type: Class<out CodeDocument>?, metadata: PathMetadata?) : super(type, metadata)
}


internal class MongoQueryTest {
    @Test
//    @Disabled
    internal fun sample() {
        val mongo = MongoClient()

        val instance = Morphia()

        val morphia = instance.map(QCodeDocument::class.java)
        val datastore = morphia.createDatastore(mongo, "codedb")

        val query: MorphiaQuery<QCodeDocument> = MorphiaQuery(morphia, datastore, QCodeDocument::class.java)
        val first: QCodeDocument = query.fetchFirst()

        println(first)
    }
}
