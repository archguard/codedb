package org.archguard.codedb.orchestration

import org.archguard.codedb.fitness.fitness
import org.junit.jupiter.api.Test
import java.lang.Math.sin

internal class WorkflowTest {
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
                input = repo(project.repo)
                // we don't need to config output, it will be automatically generated
                output = auto()

                action {
                    git("clone").source(project.repo).target().execute()
                }
            }

            task("TaskName2") {
                runtime("docker:lastest")

                after("TaskName", "TaskName 1")

                input = dir("src/main")
                output = file("loc.json")

                action {
                    println(input)
                }
            }

            task("function") {
                input = listOf(file("loc.json"), file("loc2.json"))
            }
        }

        fitness("FitnessName") {
            val result = sin(1.0 * 2.0)

            // send output
            detail["sin"] = listOf(result)

            // a fitness function call be output a value only
            when {
                result > 0.5 -> {
                    println("pass")
                }

                else -> {
                    println("fail")
                }
            }
        }
    }
}
