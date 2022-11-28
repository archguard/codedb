package org.archguard.codedb.orchestration

import org.junit.jupiter.api.Test

internal class WorkflowKtTest {
    @Test
    fun dsl_sample() {
        workflow("WorkflowName") {
            task("CreateData") {
                output = String::class
                taskAction = {
                    listOf("Hello", "World")
                }
            }
            task("TaskName2") {
                after("TaskName", "TaskName 1")

                input = String::class
                output = String::class

                taskAction {
                    val input = it as String
                    println(input)
                }
            }
            handler("sample") {

            }
        }
    }
}
