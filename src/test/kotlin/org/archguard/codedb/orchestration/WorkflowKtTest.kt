package org.archguard.codedb.orchestration

import org.junit.jupiter.api.Test

internal class WorkflowKtTest {
    @Test
    fun dsl_sample() {
        workflow("WorkflowName") {
            trigger {
                onetime("2021-10-01T00:00:00Z")
                cron("0 0 0 * * ?")
                external {
                    // do something
                }
            }

            task("CreateData") {

                input = "src/main"
                output = database("sample")

                taskAction = {
                    listOf("Hello", "World")
                }
            }
            task("TaskName2") {
                after("TaskName", "TaskName 1")

                input = "src/main"
                output = file("loc.json")

                taskAction {
                    val input = it as String
                    println("taskName:  ${input.javaClass.simpleName}")
                }
            }

            handler("sample") {

            }
        }
    }
}
