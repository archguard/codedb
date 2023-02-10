package org.archguard.runner.serial

import com.charleskorn.kaml.YamlList
import com.charleskorn.kaml.YamlMap
import com.charleskorn.kaml.YamlNode
import com.charleskorn.kaml.YamlScalar
import com.charleskorn.kaml.yamlList
import com.charleskorn.kaml.yamlMap
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ArraySerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.PolymorphicKind
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.buildSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.descriptors.nullable
import kotlinx.serialization.encoding.CompositeDecoder
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
    class Mapping(val key: kotlin.String, val value: Scalar) : Scalar() {
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
            return "{" + values.joinToString(", ") + "}"
        }
    }
}


object ScalarSerializer : KSerializer<Scalar> {
    @OptIn(InternalSerializationApi::class, ExperimentalSerializationApi::class)
    override val descriptor: SerialDescriptor by lazy {
        buildSerialDescriptor("Scalar", PolymorphicKind.SEALED) {
            element("mapping", defer { Scalar.Mapping.serializer().descriptor })

            element("object", defer { Scalar.Object.serializer().descriptor })
            element("list", defer { ListSerializer(ScalarSerializer).descriptor })

            element("string", defer { String.serializer().descriptor })
            element("number", defer { Double.serializer().descriptor })
            element("boolean", defer { Boolean.serializer().descriptor })
        }
    }

    override fun deserialize(decoder: Decoder): Scalar {
        return decoder.decodeStructure(descriptor) {
            println("decodeStructure: $descriptor, index: ${decodeElementIndex(descriptor)})}")
            var result: Scalar? = null
            while (true) {
                result = when (val index = decodeElementIndex(descriptor)) {
                    CompositeDecoder.DECODE_DONE -> break
                    0 -> Scalar.Mapping(
                        decodeStringElement(descriptor, 0),
                        decodeSerializableElement(descriptor, 1, ScalarSerializer)
                    )

                    1 -> Scalar.Object(decodeSerializableElement(descriptor, 1, ListSerializer(ScalarSerializer)))
                    2 -> Scalar.Array(decodeSerializableElement(descriptor, 2, ListSerializer(ScalarSerializer)))
                    3 -> Scalar.String(decodeStringElement(descriptor, 3))
                    4 -> Scalar.Number(decodeDoubleElement(descriptor, 4))
                    5 -> Scalar.Boolean(decodeBooleanElement(descriptor, 5))
                    else -> error("Unexpected index: $index")
                }
            }
            result ?: Scalar.Null
        }
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
    Scalar.Mapping(key.stringify(), scalarByNode(value))
}

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


/*
 * Copyright 2017-2021 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
 */
@OptIn(ExperimentalSerializationApi::class)
private fun defer(deferred: () -> SerialDescriptor): SerialDescriptor = object : SerialDescriptor {

    private val original: SerialDescriptor by lazy(deferred)

    override val serialName: String
        get() = original.serialName
    override val kind: SerialKind
        get() = original.kind
    override val elementsCount: Int
        get() = original.elementsCount

    override fun getElementName(index: Int): String = original.getElementName(index)
    override fun getElementIndex(name: String): Int = original.getElementIndex(name)
    override fun getElementAnnotations(index: Int): List<Annotation> = original.getElementAnnotations(index)
    override fun getElementDescriptor(index: Int): SerialDescriptor = original.getElementDescriptor(index)
    override fun isElementOptional(index: Int): Boolean = original.isElementOptional(index)
}