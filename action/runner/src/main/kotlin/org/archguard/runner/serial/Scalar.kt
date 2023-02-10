package org.archguard.runner.serial

import com.charleskorn.kaml.YamlList
import com.charleskorn.kaml.YamlMap
import com.charleskorn.kaml.YamlNode
import com.charleskorn.kaml.YamlScalar
import com.charleskorn.kaml.yamlList
import com.charleskorn.kaml.yamlMap
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure

@Serializable(with = ScalarSerializer::class)
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
    class Mapping(val key: kotlin.String, val value: Scalar ) : Scalar() {
        override fun equals(other: Any?): kotlin.Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Mapping

            if (key != other.key) return false
            if (value != other.value) return false

            return true
        }

        override fun hashCode(): Int {
            var result = key.hashCode()
            result = 31 * result + value.hashCode()
            return result
        }

        override fun toString(): kotlin.String {
            return "$key: $value"
        }
    }

    @Serializable
    class Object(val values: List<Scalar>) : Scalar() {
        override fun equals(other: Any?): kotlin.Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Object

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
}

object ScalarSerializer : KSerializer<Scalar> {
    override val descriptor: SerialDescriptor  = PrimitiveSerialDescriptor("Scalar", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Scalar {
        val string = decoder.decodeString()
        return Scalar.String(string)
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun serialize(encoder: Encoder, value: Scalar) {
        when (value) {
            is Scalar.Null -> encoder.encodeNull()
            is Scalar.String -> encoder.encodeString(value.value)
            is Scalar.Boolean -> encoder.encodeBoolean(value.value)
            is Scalar.Number -> encoder.encodeDouble(value.value)
            is Scalar.Array -> encoder.encodeSerializableValue(ListSerializer(ScalarSerializer), value.values)
            is Scalar.Object -> {
                val map = value.values.associate { (it as Scalar.Mapping).key to it.value }
                encoder.encodeSerializableValue(MapSerializer(String.serializer(), ScalarSerializer), map)
            }
            is Scalar.Mapping -> {
                encoder.encodeString(value.key + ": " + value.value.toString())
            }
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
private fun fromObject(yamlMap: YamlMap): List<Scalar> = yamlMap.entries.map { (key, value) ->
    Scalar.Mapping(key.stringify(), scalarByNode(value)) }

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
