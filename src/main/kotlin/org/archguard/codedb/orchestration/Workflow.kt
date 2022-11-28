package org.archguard.codedb.orchestration

import kotlin.reflect.KClass

class TaskDeclaration {
    fun input(function: () -> Any) {}

    fun output(function: () -> Any) {}

    fun taskAction(function: (input: Any) -> Any) {}

    fun dependsOn(vararg taskName: String) {
        println(taskName)
    }

    var input: KClass<String>? = null

    var output: KClass<Integer>? = null

    var taskAction: () -> Unit = {}
}

class WorkflowDeclaration(val flowName: String) {
    var tasks: HashMap<String, TaskDeclaration> = HashMap()

    fun task(taskName: String, function: TaskDeclaration.() -> Unit) {
        val taskDeclaration = TaskDeclaration()
        tasks[taskName] = taskDeclaration
        taskDeclaration.function()
    }

    fun handler(handlerName: String, function: () -> Unit) {}
}

public fun workflow(flowName: String, function: WorkflowDeclaration.() -> Unit) {
    val workflowDeclaration = WorkflowDeclaration(flowName)
    workflowDeclaration.function()
}
