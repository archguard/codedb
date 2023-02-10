package org.archguard.runner.serial

import com.charleskorn.kaml.YamlList
import com.charleskorn.kaml.YamlMap
import com.charleskorn.kaml.YamlNode
import com.charleskorn.kaml.YamlScalar
import com.charleskorn.kaml.yamlList
import com.charleskorn.kaml.yamlMap
import kotlinx.serialization.Serializable

@Serializable
sealed class Scalar {
    @Serializable
    object Null : Scalar()

    @Serializable
    data class String(val value: kotlin.String) : Scalar() {}

    @Serializable
    class Boolean(val value: kotlin.Boolean) : Scalar() {
        override fun equals(other: Any?): kotlin.Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Boolean

            if (value != other.value) return false

            return true
        }

        override fun hashCode(): Int {
            return value.hashCode()
        }

        override fun toString(): kotlin.String {
            return "$value"
        }


    }

    @Serializable
    class Number(val value: Double) : Scalar() {
        override fun equals(other: Any?): kotlin.Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Number

            if (value != other.value) return false

            return true
        }

        override fun hashCode(): Int {
            return value.hashCode()
        }

        override fun toString(): kotlin.String {
            return "$value"
        }
    }

    @Serializable
    class Array(val values: List<Scalar>) : Scalar() {
        override fun equals(other: Any?): kotlin.Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Array

            if (values != other.values) return false

            return true
        }

        override fun hashCode(): Int {
            return values.hashCode()
        }

        override fun toString(): kotlin.String {
            return values.toString()
        }
    }

    @Serializable
    class Object(val value: List<Scalar>) : Scalar() {
        override fun equals(other: Any?): kotlin.Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Object

            if (value != other.value) return false

            return true
        }

        override fun hashCode(): Int {
            return value.hashCode()
        }

        override fun toString(): kotlin.String {
            return value.toString()
        }
    }
}

fun Scalar.Companion.from(prop: YamlNode): Scalar {
    return when (prop) {
        is YamlScalar -> fromString(prop)
        is YamlList -> Scalar.Array(fromArray(prop))
        is YamlMap -> Scalar.Object(fromObject(prop))
        else -> Scalar.Null
    }
}

private fun fromArray(value: YamlList): List<Scalar> = value.items.map(::scalarByNode)
private fun fromObject(yamlMap: YamlMap): List<Scalar> = yamlMap.entries.map { (key, value) -> scalarByNode(value) }

private fun scalarByNode(value: YamlNode) = when (value) {
    is YamlScalar -> fromString(value)
    is YamlList -> Scalar.Array(fromArray(value.yamlList))
    is YamlMap -> Scalar.Object(fromObject(value.yamlMap))
    else -> Scalar.Null
}

private fun fromString(origin: YamlScalar): Scalar = Scalar.fromString(origin.stringify())

fun Scalar.Companion.fromString(value: String): Scalar {
    return when {
        isBoolean(value) -> Scalar.Boolean(java.lang.Boolean.parseBoolean(value))
        isNumber(value) -> Scalar.Number(value.toDouble())
        else -> Scalar.String(value)
    }
}

private fun isBoolean(value: String): Boolean {
    return value == "true" || value == "false"
}

private fun isNumber(value: String): Boolean {
    return value.matches("-?\\d+(\\.\\d+)?".toRegex())
}
