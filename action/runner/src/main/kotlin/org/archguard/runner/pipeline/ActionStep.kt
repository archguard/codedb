package org.archguard.runner.pipeline

import com.charleskorn.kaml.YamlList
import com.charleskorn.kaml.YamlMap
import com.charleskorn.kaml.YamlNode
import com.charleskorn.kaml.YamlScalar
import com.charleskorn.kaml.yamlList
import com.charleskorn.kaml.yamlMap
import kotlinx.serialization.Serializable
import java.lang.Boolean.parseBoolean

@Serializable
class ActionStep(
    var name: String = "",
    var description: String = "",
    var enabled: Boolean = true,
    var uses: String = "",
    var with: HashMap<String, Scalar> = hashMapOf(),
) {
}

@Serializable
sealed class Scalar {
    @Serializable
    object Null : Scalar()

    @Serializable
    data class String(val value: kotlin.String) : Scalar()

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
    }

    companion object {
        fun from(prop: YamlNode): Scalar {
            return when (prop) {
                is YamlScalar -> fromString(prop)
                is YamlList -> Array(fromArray(prop))
                is YamlMap -> Object(fromObject(prop))
                else -> Null
            }
        }

        private fun fromArray(value: YamlList): List<Scalar> = value.items.map(::scalarByNode)
        private fun fromObject(yamlMap: YamlMap): List<Scalar> =
            yamlMap.entries.map { (key, value) -> scalarByNode(value) }

        private fun scalarByNode(value: YamlNode) = when (value) {
            is YamlScalar -> fromString(value)
            is YamlList -> Array(fromArray(value.yamlList))
            is YamlMap -> Object(fromObject(value.yamlMap))
            else -> Null
        }

        private fun fromString(origin: YamlScalar): Scalar = fromString(origin.stringify())

        fun fromString(value: kotlin.String): Scalar {
            return when {
                isBoolean(value) -> Boolean(parseBoolean(value))
                isNumber(value) -> Number(value.toDouble())
                isQuoteString(value) -> String(value.substring(1, value.length - 1))
                else -> String(value)
            }
        }

        private fun isQuoteString(value: kotlin.String): kotlin.Boolean {
            return value.matches("\".*\"".toRegex()) || value.matches("'.*'".toRegex())
        }

        private fun isBoolean(value: kotlin.String): kotlin.Boolean {
            return value == "true" || value == "false"
        }

        private fun isNumber(value: kotlin.String): kotlin.Boolean {
            return value.matches("-?\\d+(\\.\\d+)?".toRegex())
        }
    }
}
