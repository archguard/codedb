package org.archguard.codedb.orchestration

import org.junit.jupiter.api.Test

internal class WorkflowKtTest {
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
        // .binding()
    }
}