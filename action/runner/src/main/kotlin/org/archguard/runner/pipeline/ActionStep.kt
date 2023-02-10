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
        fun from(prop: YamlNode): Scalar {
            return when (prop.javaClass) {
                YamlScalar::class.java -> {
                    from(prop as YamlScalar)
                }

                YamlList::class.java -> {
                    Array(fromArray(prop as YamlList))
                }

                YamlMap::class.java -> {
                    Object(fromObject(prop as YamlMap))
                }

                else -> {
                    Null
                }
            }
        }

        private fun from(origin: YamlScalar): Scalar = from(origin.stringify())

        fun from(value: kotlin.String): Scalar {
            return when {
                isBoolean(value) -> Boolean(parseBoolean(value))
                isNumber(value) -> Number(value.toDouble())
                isQuoteString(value) -> {
                    String(value.substring(1, value.length - 1))
                }

                else -> {
                    String(value)
                }
            }
        }

        private fun fromArray(value: YamlList): List<Scalar> {
            return value.items.map { prop ->
                when (prop) {
                    is YamlScalar -> {
                        from(prop)
                    }

                    is YamlList -> {
                        Array(fromArray(prop.yamlList))
                    }

                    is YamlMap -> {
                        Object(fromObject(prop.yamlMap))
                    }

                    else -> {
                        Null
                    }
                }
            }
        }

        private fun fromObject(yamlMap: YamlMap): List<Scalar> {
            return yamlMap.entries.map { (key, value) ->
                when (value) {
                    is YamlScalar -> {
                        from(value)
                    }

                    is YamlList -> {
                        Array(fromArray(value.yamlList))
                    }

                    is YamlMap -> {
                        Object(fromObject(value.yamlMap))
                    }

                    else -> {
                        Null
                    }
                }
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
