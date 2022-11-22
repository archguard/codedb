package org.archguard.codedb.automate.task.core

/**
 * intermediate will create a new document/table/schema in the database
 */
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.FIELD
)
annotation class Intermediate(val name: String = "")
