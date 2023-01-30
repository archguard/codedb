package org.archguard.codedb.coverage

import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties


class BeanToSql {
    fun bean2Sql(bean: Any): String {
        val clazz = bean::class
        val table = clazz.findAnnotation<Sql>()?.value ?: clazz.simpleName
        val columns = arrayListOf<String>()
        val values = arrayListOf<Any?>()
        clazz.memberProperties.forEach { property ->
            val column = property.findAnnotation<Sql>()?.value ?: property.name
            columns.add(column)
            values.add(property.call(bean))
        }
        val valueString = values.joinToString {
            if (it is String) {
                "'${it.replace("'", "''")}'"// do with like it's -- there is a ' within string
            } else if (it is Enum<*>) {
                "'$it'"
            } else it.toString()
        }

        return "insert into test_coverage_$table(${columns.joinToString()}) values($valueString);"
    }
}
