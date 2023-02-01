package org.archguard.codedb.orchestration

import org.archguard.codedb.orchestration.io.GitHandler
import org.archguard.codedb.orchestration.io.HandlerType
import org.archguard.codedb.orchestration.io.HttpMethod
import org.archguard.codedb.orchestration.schedule.CronTime
import kotlin.reflect.KClass

class Workflow {

}

/**
 *
 * Task is a unit of work in a workflow.
 *
 * @param name workflow name
 * @param block workflow block
 *
 * examples:
 * 
 * ```kotlin
 * task("TaskName2") {
 *     runtime {
 *         image("archguard/archguard-backend:latest")
 *     }
 *
 *     after("TaskName", "TaskName 1")
 *
 *     input = dir("src/main")
 *     output = file("loc.json")
 *
 *     action {
 *         println(input)
 *     }
 * }
 * ```
 */
class TaskDeclaration(val taskName: String) {
    var input: HandlerType? = null
    var output: HandlerType? = null
    var runtime: RuntimeDeclaration? = null

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
     * stdout is a special handler, it will be automatically generated
     */
    fun stdout() = HandlerType.Stdout()

    /**
     * keep api to Kotlin language
     */
    fun listOf(vararg handlerTypes: HandlerType) = HandlerType.Multiple(handlerTypes)
    fun multiple(vararg handlerTypes: HandlerType) = HandlerType.Multiple(handlerTypes)

    /**
     * auto means do nothing, just to make conceptual completeness
     */
    fun auto() = HandlerType.Auto()

    /**
     * Runtime is a container to run the task, default to use docker
     *
     * @param function the name of the runtime
     *
     *  for example: archguard/archguard-backend:latest
     */
    fun runtime(function: RuntimeDeclaration.() -> Unit) {
        val runtimeDeclaration = RuntimeDeclaration()
        runtimeDeclaration.function()

        // after execute, align the project
        runtime = runtimeDeclaration
    }
}

class RuntimeDeclaration {
    fun image(imageName: String) {

    }
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
        CronTime.from(expression)
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

fun workflow(flowName: String, function: WorkflowDeclaration.() -> Unit) {
    val workflowDeclaration = WorkflowDeclaration(flowName)
    workflowDeclaration.function()
}

fun git(command: String): GitHandler {
    return GitHandler(command)
}