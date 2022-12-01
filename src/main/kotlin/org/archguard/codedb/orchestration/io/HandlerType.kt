package org.archguard.codedb.orchestration.io

import kotlin.reflect.KClass

sealed class HandlerType {
    class Auto() : HandlerType()
    class Repo(val url: String) : HandlerType() {

    }

    class Http(val url: String, val method: HttpMethod, val data: Any) : HandlerType() {

    }

    class Database(val tableName: String) : HandlerType() {

    }

    class File(val fileName: String) : HandlerType() {

    }

    class Dir(val dirName: String) : HandlerType() {

    }

    class Custom(val handler: KClass<*>) : HandlerType() {

    }

    class Multiple(val handlerTypes: Array<out HandlerType>) : HandlerType() {

    }

    class Stdout: HandlerType() {

    }

    override fun toString(): String {
        return when (this) {
            is Auto -> "auto"
            is Repo -> "repo $url"
            is Http -> "http $method $url"
            is Database -> "database $tableName"
            is File -> "file $fileName"
            is Dir -> "dir $dirName"
            is Custom -> "custom"
            is Multiple -> "multiple $handlerTypes"
            is Stdout -> "stdout"
        }
    }
}