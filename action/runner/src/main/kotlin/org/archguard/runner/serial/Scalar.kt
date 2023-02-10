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
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.PolymorphicKind
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.descriptors.buildSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject

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
    class List(val value: kotlin.collections.List<Scalar>) : Scalar() {
        override fun equals(other: Any?): kotlin.Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as List

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

    @Serializable
    class Object(val value: Map<kotlin.String, Scalar>) : Scalar() {
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


object ScalarSerializer : KSerializer<Scalar> {
    @OptIn(InternalSerializationApi::class, ExperimentalSerializationApi::class)
    override val descriptor: SerialDescriptor by lazy {
        buildSerialDescriptor("Scalar", PolymorphicKind.SEALED) {
            element("String", defer { String.serializer().descriptor })
            element("Number", defer { Double.serializer().descriptor })
            element("Boolean", defer { Boolean.serializer().descriptor })
            element("Object", defer { JsonObject.serializer().descriptor })
            element("List", defer { JsonArray.serializer().descriptor })
        }
    }

    override fun deserialize(decoder: Decoder): Scalar {
        return decoder.decodeStructure(descriptor) {
            when (val index = decodeElementIndex(descriptor)) {
                0 -> Scalar.String(decodeStringElement(descriptor, index))
                1 -> Scalar.Number(decodeDoubleElement(descriptor, index))
                2 -> Scalar.Boolean(decodeBooleanElement(descriptor, index))
                3 -> {
                    val mapSerializer = MapSerializer(String.serializer(), ScalarSerializer)
                    Scalar.Object(decodeSerializableElement(descriptor, index, mapSerializer))
                }
                4 -> {
                    val listSerializer = ListSerializer(ScalarSerializer)
                    Scalar.List(decodeSerializableElement(descriptor, index, listSerializer))
                }
                else -> throw IllegalStateException("Unexpected index $index")
            }
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun serialize(encoder: Encoder, value: Scalar) {
        when (value) {
            is Scalar.Null -> encoder.encodeNull()
            is Scalar.String -> encoder.encodeString(value.value)
            is Scalar.Boolean -> encoder.encodeBoolean(value.value)
            is Scalar.Number -> encoder.encodeDouble(value.value)
            is Scalar.List -> encoder.encodeSerializableValue(ListSerializer(ScalarSerializer), value.value)
            is Scalar.Object -> {
                val mapSerializer = MapSerializer(String.serializer(), ScalarSerializer)
                encoder.encodeSerializableValue(mapSerializer, value.value)
            }
        }
    }
}

fun Scalar.Companion.from(prop: YamlNode): Scalar {
    return when (prop) {
        is YamlScalar -> fromString(prop)
        is YamlList -> Scalar.List(fromArray(prop))
        is YamlMap -> Scalar.Object(fromObject(prop))
        else -> Scalar.Null
    }
}

private fun fromArray(value: YamlList): List<Scalar> = value.items.map(::scalarByNode)
private fun fromObject(yamlMap: YamlMap): Map<String, Scalar> = yamlMap.entries.map { (key, value) ->
    key.stringify() to scalarByNode(value)
}.toMap()

private fun scalarByNode(value: YamlNode) = when (value) {
    is YamlScalar -> fromString(value)
    is YamlList -> Scalar.List(fromArray(value.yamlList))
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