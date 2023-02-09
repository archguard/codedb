package org.archguard.runner.pipeline

import kotlinx.serialization.Serializable

@Serializable
class ActionStep(
    val name: String = "",
    val description: String = "",
    val enabled: Boolean = true,
    var uses: String = "",
    var with: HashMap<String, ScalarValue> = hashMapOf()
) {
}


@Serializable
class ScalarValue(
    val value: String = "",
    val kind: ScalarType = ScalarType.String,
    val isList: Boolean = false
)

enum class ScalarType {
    String,
    Boolean,
    Number,
    Array,
    Object
}
