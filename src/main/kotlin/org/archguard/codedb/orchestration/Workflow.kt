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

sealed class HttpMethod {
    object GET : HttpMethod()
    object POST : HttpMethod()
    object PUT : HttpMethod()
    object DELETE : HttpMethod()
    object PATCH : HttpMethod()
    object HEAD : HttpMethod()
    object OPTIONS : HttpMethod()
    object TRACE : HttpMethod()
    object CONNECT : HttpMethod()
}

class IOHandler {
    constructor()

    fun database(tableName: String): Boolean {
        return true
    }

    fun file(fileName: String): Boolean {
        return true
    }

    // output type: dr
    fun dir(dirName: String): Boolean {
        return true
    }

    // todo: add streaming for http
    fun http(url: String, method: HttpMethod, data: Any): Boolean {
        return true
    }
}

sealed class HandlerType {
    class Auto() : HandlerType()
    class Repo(val url: String) : HandlerType() {

    }

    class Http(val url: String, val method: HttpMethod, val data: Any) : HandlerType() {

    }

    class Database(val tableName: String) : HandlerType() {

    }

    class File(val fileName: String) : HandlerType() {

    }

    class Dir(val dirName: String) : HandlerType() {

    }

    class Custom(val handler: KClass<*>) : HandlerType() {

    }

    override fun toString(): String {
        return when (this) {
            is Auto -> "auto"
            is Repo -> "repo $url"
            is Http -> "http $method $url"
            is Database -> "database $tableName"
            is File -> "file $fileName"
            is Dir -> "dir $dirName"
            is Custom -> "custom"
        }
    }
}

class TaskDeclaration(val taskName: String) {
    var input: HandlerType? = null
    var output: HandlerType? = null

    fun input(function: () -> Any) = function()
    fun output(function: () -> Any) = function()
    fun action(function: () -> Any) = function()

    fun after(vararg taskNames: String) {
        println("after: ${taskNames.joinToString(",")}")
    }

    fun before(vararg taskNames: String) {
        println(taskNames)
    }

    /**
     * for current support for MongoDB only
     */
    fun database(tableName: String) = HandlerType.Database(tableName)
    fun file(fileName: String) = HandlerType.File(fileName)
    fun dir(dirName: String) = HandlerType.Dir(dirName)
    fun http(url: String, method: HttpMethod, data: Any) = HandlerType.Http(url, method, data)
    fun repo(url: String) = HandlerType.Repo(url)

    /**
     * auto means do nothing, just to make conceptual completeness
     */
    fun auto() = HandlerType.Auto()
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

class WorkflowDeclaration(val flowName: String, var project: ProjectDeclaration = ProjectDeclaration("default")) {
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

    fun project(projectName: String, function: ProjectDeclaration.() -> Unit) {
        val projectDeclaration = ProjectDeclaration(projectName)
        projectDeclaration.function()

        // after execute, align the project
        project = projectDeclaration
    }
}

class ProjectDeclaration(var name: String, var repo: String = "") {

    fun name(name: String) {
        this.name = name
    }

    fun repo(url: String) {
        this.repo = url
    }
}

public fun workflow(flowName: String, function: WorkflowDeclaration.() -> Unit) {
    val workflowDeclaration = WorkflowDeclaration(flowName)
    workflowDeclaration.function()
}

fun git(command: String): GitHandler {
    return GitHandler(command)
}