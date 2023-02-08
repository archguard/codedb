package org.archguard.runner.pipeline

class ActionStep : JobStep() {
}

abstract class JobStep : Step() {

}

abstract class Step() {
    val name: String = ""
    val description: String = ""

}
