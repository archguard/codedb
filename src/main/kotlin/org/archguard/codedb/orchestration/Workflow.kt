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

class IOHandler() {
    fun database(tableName: String): Boolean {
        return true
    }

    // output type: file
    fun file(fileName: String): Boolean {
        return true
    }

    // output type: dir
    fun dir(dirName: String): Boolean {
        return true
    }

    // output type: http
    fun http(url: String): Boolean {
        return true
    }
}

class TaskDeclaration(val taskName: String) {
    private val handler: IOHandler = IOHandler()

    var input: Any? = null
    var output: Any? = null

    var taskAction: () -> Unit = {}


    fun input(function: () -> Any) {
        function()
    }

    fun output(function: () -> Any) {
        function()
    }

    fun taskAction(function: (input: Any) -> Any) {

    }

    fun after(vararg taskNames: String) {
        println("after: ${taskNames.joinToString(",")}")
    }

    fun before(vararg taskNames: String) {
        println(taskNames)
    }

    /**
     * for current support for MongoDB only
     */
    fun database(tableName: String) = handler.database(tableName)
    fun file(fileName: String) = handler.file(fileName)
    fun dir(dirName: String) = handler.dir(dirName)
    fun http(url: String) = handler.http(url)
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
    fun once(time: String) {

    }

    /**
     * Cron Trigger
     */
    fun cron(expression: String) {

    }

    /**
     * Schedule Trigger
     */
    fun schedule(expression: String) {
        if (expression.isEmpty()) {
            throw IllegalArgumentException("Invalid schedule expression: $expression")
        }

        // handle for like @daily
        if (expression.startsWith("@")) {
            val cronTime = CronTime(0, 0, 0, 0, 0, 0, 0)
            when (expression) {
                "@daily" -> cronTime.from("0 0 0 * * ?")
                "@weekly" -> cronTime.from("0 0 0 ? * MON")
                "@monthly" -> cronTime.from("0 0 0 1 * ?")
                "@yearly" -> cronTime.from("0 0 0 1 1 ?")
                else -> throw IllegalArgumentException("Invalid schedule expression: $expression")
            }
        }

        if (expression.startsWith("cron:")) {
            val cronTime = CronTime(0, 0, 0, 0, 0, 0, 0)
            cronTime.from(expression.substring(5))
        }
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

    fun trigger(function: TriggerDeclaration.() -> Unit) {

    }

    fun handler(handlerName: String, function: () -> Unit) {}

    fun binding(bindingName: String, function: () -> Unit) {}
}

public fun workflow(flowName: String, function: WorkflowDeclaration.() -> Unit) {
    val workflowDeclaration = WorkflowDeclaration(flowName)
    workflowDeclaration.function()
}
