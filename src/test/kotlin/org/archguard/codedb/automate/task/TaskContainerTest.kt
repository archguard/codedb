package org.archguard.codedb.automate.task

import org.archguard.codedb.automate.builtin.LocTask
import org.junit.jupiter.api.Test

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
                input {
                    "String"
                }
                output {

                }
                taskAction {

                }
            }
            handler("sample") {

            }
        }
    }

    class TaskDeclaration {
        fun input(function: () -> Any) {}
        fun output(function: () -> Any) {}
        fun taskAction(function: () -> Any) {}

        var input: ()  -> Unit = {}
        var output: () -> Unit = {}
        var taskAction: () -> Unit = {}
    }

    private fun handler(handleName: String, function: () -> Unit) {
        println("handler: $handleName")
    }

    private fun task(taskName: String, function: TaskDeclaration.() -> Unit) {
        println("task: $taskName")
    }

    private fun workflow(flowName: String, function: () -> Unit) {
        println("Workflow: $flowName")
        function()
    }
}