package org.archguard.codedb.orchestration

import kotlin.reflect.KClass

class CronTime(
    val second: Int,
    val minute: Int,
    val hour: Int,
    val dayOfMonth: Int,
    val month: Int,
    val dayOfWeek: Int,
    val year: Int
) {
    fun from(str: String): CronTime {
        val tokens = str.split(" ")
        if (tokens.size != 6) {
            throw IllegalArgumentException("Invalid cron time format: $str")
        }
        return CronTime(
            second = tokens[0].toInt(),
            minute = tokens[1].toInt(),
            hour = tokens[2].toInt(),
            dayOfMonth = tokens[3].toInt(),
            month = tokens[4].toInt(),
            dayOfWeek = tokens[5].toInt(),
            year = 0
        )
    }
}

class TaskDeclaration(val taskName: String) {
    var input: KClass<*>? = null
    var output: KClass<*>? = null

    fun input(function: () -> Any) {
        function()
    }

    fun output(function: () -> Any) {
        function()
    }

    fun taskAction(function: (input: Any) -> Any) {

    }

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
        function()
    }

    /**
     * One Time Trigger
     */
    fun onetime(time: String) {

    }

    /**
     * Cron Trigger
     */
    fun cron(expression: String) {

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
