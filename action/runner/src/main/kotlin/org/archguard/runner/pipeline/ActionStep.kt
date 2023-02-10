package org.archguard.runner.pipeline

import kotlinx.serialization.Serializable
import org.archguard.runner.serial.Scalar

@Serializable
data class ActionStep(
    var name: String = "",
    var description: String = "",
    var enabled: Boolean = true,
    var uses: String = "",
    var with: HashMap<String, Scalar> = hashMapOf(),
) {
}

