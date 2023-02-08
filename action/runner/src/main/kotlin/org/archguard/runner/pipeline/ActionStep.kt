package org.archguard.runner.pipeline

class ActionStep : JobStep() {
}

abstract class JobStep : Step() {

}

abstract class Step() {
    val id: String = ""
    val name: String = ""
    val description: String = ""
    val enabled: Boolean = true
}
