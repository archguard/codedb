package org.archguard.codedb.query

import chapi.domain.core.CodeDataStruct
import com.mongodb.*
import com.mongodb.client.MongoDatabase
import com.querydsl.core.types.ConstantImpl
import com.querydsl.core.types.Ops
import com.querydsl.core.types.PathMetadata
import com.querydsl.core.types.PathMetadataFactory
import com.querydsl.core.types.dsl.*
import com.querydsl.mongodb.morphia.MorphiaQuery
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.archguard.codedb.factor.quality.Coverage
import org.archguard.codedb.factor.quality.QCoverage
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mongodb.morphia.Datastore
import org.mongodb.morphia.Morphia
import org.mongodb.morphia.annotations.Entity
import org.mongodb.morphia.annotations.Id
import org.mongodb.morphia.mapping.Mapper

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

    companion object {
        val language: Any = Expressions.constant("Java")
    }
}

internal class MongoQueryTest {
    @MockK
    private lateinit var datastore: Datastore


    @MockK
    private lateinit var dbCollection: DBCollection


    @MockK
    private lateinit var mapper: Mapper

    private lateinit var morphia: Morphia

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        val database = mockk<MongoDatabase>()
        every { database.name } returns "codedb"

        val mongo = mockk<MongoClient>();
        val db = mockk<DB>()
        val cursor = mockk<DBCursor>()

        val iterator = mockk<MutableIterator<DBObject>>()
        every { iterator.hasNext() } returns false

        every { cursor.iterator() } returns iterator

        // todo: verify spy
        every { dbCollection.find(any(), any<DBObject>()) } returns cursor

        every { db.getCollection(any()) } returns dbCollection

        every { mongo.getDatabase(any()) } returns database
        every { mongo.getDB(any()) } returns db
        every { mongo.writeConcern } returns mockk()

        morphia = Morphia().map(CodeDocument::class.java)
        datastore = morphia.createDatastore(mongo, "codedb")
    }


    @Test
    internal fun sample() {
        val query: MorphiaQuery<CodeDocument> = MorphiaQuery(morphia, datastore, CodeDocument::class.java)

        val where = query
            .where(
                Expressions.booleanOperation(Ops.EQ, Expressions.stringPath("language"), ConstantImpl.create("kotlin"))
            )

        assertEquals(where.toString(), "{ \"language\" : \"kotlin\"}")
    }

    @Test
    internal fun shouldQueryByLanguage() {
        val query: MorphiaQuery<Coverage> = MorphiaQuery(morphia, datastore, Coverage::class.java)

        val qRule = QCoverage.coverage

        val where = query.where(qRule.value.between(0.0, 1.0))

        assertEquals(where.toString(), "{ \"value\" : { \"${"\$"}gte\" : 0.0 , \"${"\$"}lte\" : 1.0}}")
    }

    @Test
    internal fun `should query with string path`() {
        val query: MorphiaQuery<CodeDocument> = MorphiaQuery(morphia, datastore, CodeDocument::class.java)

        val where = query
            .where(string("language").eq("kotlin"))
            .orderBy(string("language").asc())

        assertEquals(where.toString(), "{ \"language\" : \"kotlin\"}")
    }

    // todo: move to field
    private fun string(name: String): StringPath {
        return Expressions.stringPath(name)
    }
}
