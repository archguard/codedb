package org.archguard.runner.pipeline

import kotlinx.serialization.Serializable
import java.lang.Boolean.parseBoolean

@Serializable
class ActionStep(
    var name: String = "",
    var description: String = "",
    var enabled: Boolean = true,
    var uses: String = "",
    var with: HashMap<String, Scalar> = hashMapOf()
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

    }

    companion object {
        fun from(value: kotlin.String): Scalar {
            return when {
                isBoolean(value) -> Boolean(parseBoolean(value))
                isNumber(value) -> Number(value.toDouble())
                isArray(value) -> {
                    val values = value.substring(1, value.length - 1).split(",").map { it.trim() }
                    Array(values.map { from(it) })
                }

                isQuoteString(value) -> {
                    String(value.substring(1, value.length - 1))
                }

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

        private fun isArray(value: kotlin.String): kotlin.Boolean {
            return value.startsWith("[") && value.endsWith("]")
        }
    }
}
