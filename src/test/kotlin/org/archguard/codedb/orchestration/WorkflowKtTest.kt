package org.archguard.codedb.orchestration

import org.junit.jupiter.api.Test

internal class WorkflowKtTest {
    @Test
    fun dsl_sample() {
        workflow("WorkflowName") {
            task("CreateData") {
                trigger {
                    onetime("2021-10-01T00:00:00Z")
                    cron("0 0 0 * * ?")
                    external {
                        // do something
                    }
                }
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
