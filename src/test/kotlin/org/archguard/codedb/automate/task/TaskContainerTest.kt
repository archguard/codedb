package org.archguard.codedb.automate.task

import org.archguard.codedb.automate.builtin.LocTask
import org.junit.jupiter.api.Test
import kotlin.reflect.KClass

internal class TaskContainerTest {

    @Test
    fun `should register task`() {
        val taskContainer = TaskContainer()
        val task = taskContainer.create("Hello", LocTask::class)
        task.run()
    }

    @Test
    fun dsl_sample() {
        workflow("WorkflowName") {
            task("TaskName") {
                taskAction = {
                    println("Hello World")
                }
            }
            task("TaskName2") {
                dependsOn("TaskName", "TaskName 1")
                input = String::class
                output = Integer::class

                taskAction {
                    val input = it as String
                    println(input)
                }
            }
            handler("sample") {

            }
        }
    }

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

    class WorkflowDeclaration {
        fun task(taskName: String, function: TaskDeclaration.() -> Unit) {
            val taskDeclaration = TaskDeclaration()
            taskDeclaration.function()
            println(taskDeclaration.input)
            println(taskDeclaration.output)
            println(taskDeclaration.taskAction)
        }
        fun handler(handlerName: String, function: () -> Unit) {}
    }

    private fun workflow(flowName: String, function: WorkflowDeclaration.() -> Unit) {
        val workflowDeclaration = WorkflowDeclaration()
        workflowDeclaration.function()
    }
}

private infix fun Unit.h2(function: () -> Unit) {
    TODO("Not yet implemented")
}
