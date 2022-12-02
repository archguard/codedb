package org.archguard.codedb.query.mongo

import com.mongodb.reactivestreams.client.MongoCollection
import org.bson.Document

class NativeMongo {
    fun query(collection: MongoCollection<Document>) {
//        collection.find(`in`("status", "A", "D"))
    }

}