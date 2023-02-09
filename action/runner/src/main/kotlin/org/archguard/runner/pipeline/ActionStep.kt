package org.archguard.runner.pipeline

import kotlinx.serialization.Serializable

@Serializable
class ActionStep : JobStep() {
}

abstract class JobStep : Step() {

}

abstract class Step() {
    val name: String = ""
    val description: String = ""
    val enabled: Boolean = true
}
