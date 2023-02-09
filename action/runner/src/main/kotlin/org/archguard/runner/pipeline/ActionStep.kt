package org.archguard.runner.pipeline

import kotlinx.serialization.Serializable

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
    class Boolean(val value: kotlin.Boolean) : Scalar() {}

    @Serializable
    class Number(val value: Double) : Scalar() {}

    @Serializable
    class Array(val values: List<Scalar>) : Scalar()

    @Serializable
    class Object(val value: List<Scalar>) : Scalar()

    companion object {
        fun from(value: kotlin.String): Scalar {
            when (value) {
                "true" -> return Boolean(true)
                "false" -> return Boolean(false)
                else -> return String(value)
            }
        }
    }
}
