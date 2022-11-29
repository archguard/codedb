package org.archguard.codedb.orchestration

import org.junit.jupiter.api.Test

internal class WorkflowKtTest {
    @Test
    fun dsl_sample() {
        workflow("WorkflowName") {
            trigger {
                once("2021-10-01T00:00:00Z")
                cron("0 0 0 * * ?")
                schedule("@daily")
                external {
                    // do something
                }
            }

            project("ProjectName") {
                repo("https://github.com/feakin/fklang")
            }

            task("Create Data") {
                input = git(project.repo)
                // we don't need to config output, it will be automatically generated
                output = auto()

                action {
                    listOf("Hello", "World")
                }
            }

            task("TaskName2") {
                after("TaskName", "TaskName 1")

                input = dir("src/main")
                output = file("loc.json")

                action {
                    println(input)
                }
            }
        }
    }
}
