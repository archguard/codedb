package org.archguard.codedb.orchestration

import kotlin.reflect.KClass

class TaskDeclaration(val taskName: String) {
    var input: KClass<*>? = null
    var output: KClass<*>? = null

    fun input(function: () -> Any) {}

    fun output(function: () -> Any) {}

    fun taskAction(function: (input: Any) -> Any) {}

    fun after(vararg taskName: String) {
        println(taskName)
    }

    fun before(vararg taskName: String) {
        println(taskName)
    }

    fun trigger(function: TriggerDeclaration.() -> Unit) {

    }

    var taskAction: () -> Unit = {}
}

class TriggerDeclaration {
    /**
     * External Event trigger
     */
    fun external(function: () -> Unit) {

    }

    /**
     * One Time Trigger
     */
    fun onetime(time: String) {

    }

    /**
     * Cron Trigger
     */
    fun cron(function: () -> Unit) {

    }

    /**
     * Interval Trigger
     */
    fun interval(function: () -> Unit) {

    }
}

class WorkflowDeclaration(val flowName: String) {
    var tasks: HashMap<String, TaskDeclaration> = HashMap()

    // task @Input
    var outputMapping: HashMap<KClass<*>, TaskDeclaration> = HashMap()

    // @Output
    var inputMapping: HashMap<KClass<*>, TaskDeclaration> = HashMap()

    fun task(taskName: String, function: TaskDeclaration.() -> Unit) {
        val taskDeclaration = TaskDeclaration(taskName)
        tasks[taskName] = taskDeclaration

        taskDeclaration.function()
    }

    fun handler(handlerName: String, function: () -> Unit) {}

    fun binding(bindingName: String, function: () -> Unit) {}
}

public fun workflow(flowName: String, function: WorkflowDeclaration.() -> Unit) {
    val workflowDeclaration = WorkflowDeclaration(flowName)
    workflowDeclaration.function()
}
