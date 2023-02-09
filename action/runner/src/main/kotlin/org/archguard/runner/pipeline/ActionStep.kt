package org.archguard.runner.pipeline

import kotlinx.serialization.Serializable
import java.lang.Boolean.parseBoolean

@Serializable
class ActionStep(
    val name: String = "",
    val description: String = "",
    val enabled: Boolean = true,
    var uses: String = "",
    var with: HashMap<String, Scalar> = hashMapOf()
) {
}

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
    class Array(val values: List<Scalar>) : Scalar()

    @Serializable
    class Object(val value: List<Scalar>) : Scalar() {

    }

    companion object {
        fun from(value: kotlin.String): Scalar {
            return when {
                isBoolean(value) -> Boolean(parseBoolean(value))
                isNumber(value) -> Number(value.toDouble())
                else -> String(value)
            }
        }

        private fun isBoolean(value: kotlin.String): kotlin.Boolean {
            return value == "true" || value == "false"
        }

        private fun isNumber(value: kotlin.String): kotlin.Boolean {
            return value.matches("-?\\d+(\\.\\d+)?".toRegex())
        }
    }
}
