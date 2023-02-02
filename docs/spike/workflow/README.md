# Workflow

## Pipeliine


https://github.com/pditommaso/awesome-pipeline

## Existing Workflow systems

Links: https://github.com/common-workflow-language/common-workflow-language/wiki/Existing-Workflow-systems

Antlr Parser: [https://github.com/openwdl/wdl/tree/main/versions/1.1/parsers/antlr4](https://github.com/openwdl/wdl/tree/main/versions/1.1/parsers/antlr4)

Execution Engines:

WDL is not executable in and of itself, but requires an execution engine to run. Compliant executions engines should 
support the features of a specific version of the WDL specification. Please see the corresponding engine documentation 
for information on available execution options and support.

*   [Cromwell](https://github.com/broadinstitute/cromwell)
*   [MiniWDL](https://github.com/chanzuckerberg/miniwdl)
*   [dxWDL](https://github.com/dnanexus/dxWDL)

## Luigi

https://github.com/spotify/luigi


## Nextflow

https://github.com/nextflow-io/nextflow

Nextflow is a workflow manager that allows you to write workflows in a simple and intuitive way. It is based on the concept of dataflow programming, where the execution graph is defined by the data connections between the different process steps. This approach allows you to write complex workflows in a simple and elegant way, without the need to deal with low-level details of the execution environment.

nextflow examples

```groovy
params.str = 'Hello world!'

process splitLetters {
  output:
    path 'chunk_*'

  """
  printf '${params.str}' | split -b 6 - chunk_
  """
}

process convertToUpper {
  input:
    path x
  output:
    stdout

  """
  cat $x | tr '[a-z]' '[A-Z]'
  """
}

workflow {
  splitLetters | flatten | convertToUpper | view { it.trim() }
}
```

Nextflow builtins:

https://www.nextflow.io/docs/latest/script.html

### Script implicit variables

| Name         | Description                                                                                                                                                      |
|--------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `baseDir`    | The directory where the main workflow script is located (deprecated in favour of `projectDir` since `20.04.0`).                                                  |
| `launchDir`  | The directory where the workflow is run (requires version `20.04.0` or later).                                                                                   |
| `moduleDir`  | The directory where a module script is located for DSL2 modules or the same as `projectDir` for a non-module script (requires version `20.04.0` or later).       |
| `nextflow`   | Dictionary like object representing nextflow runtime information (see [Nextflow metadata](https://www.nextflow.io/docs/latest/metadata.html#metadata-nextflow)). |
| `params`     | Dictionary like object holding workflow parameters specifing in the config file or as command line options.                                                      |
| `projectDir` | The directory where the main script is located (requires version `20.04.0` or later).                                                                            |
| `workDir`    | The directory where tasks temporary files are created.                                                                                                           |
| `workflow`   | Dictionary like object representing workflow runtime information (see [Runtime metadata](https://www.nextflow.io/docs/latest/metadata.html#metadata-workflow)).  |

### Configuration implicit variables

| Name          | Description                                                                                                     |
|---------------|-----------------------------------------------------------------------------------------------------------------|
| `baseDir`     | The directory where the main workflow script is located (deprecated in favour of `projectDir` since `20.04.0`). |
| `launchDir`   | The directory where the workflow is run (requires version `20.04.0` or later).                                  |
| `projectDir`  | The directory where the main script is located (requires version `20.04.0` or later).                           |

# Computation orchestration



## Beethoven: An Event-Driven Lightweight Platform for Microservice Orchestration

Sample: [https://github.com/davimonteiro/beethoven/blob/master/beethoven-app/src/main/resources/workflows/newConsumerProcess.partitur](https://github.com/davimonteiro/beethoven/blob/master/beethoven-app/src/main/resources/workflows/newConsumerProcess.partitur)


DSL GitHub: [https://github.com/davimonteiro/partitur](https://github.com/davimonteiro/partitur)

```kotlin
workflow newConsumerProcess {
	task createNewConsumer {
		post("http://consumer-service/consumers")
		    .header("Content-Type", "application/json")
		    .body("${input}")
	}
	task analyzeConsumerProfile {
		post("http://profile-service/profiles/analyze")
		    .header("Content-Type", "application/json")
		    .body("${createNewConsumer.response}")
	}
	task sendWelcomeEmail {
		post("http://email-service/welcome")
		    .header("Content-Type", "application/json")
		    .body("${analyzeConsumerProfile.response}")
	}
	task sendWelcomePackage {
		post("http://package-service/welcome")
		    .header("Content-Type", "application/json")
            .body("${analyzeConsumerProfile.response}")
	}
	task sendAccountCardAndPassword {
		post("http://account-service/cards-passwords")
		    .header("Content-Type", "application/json")
            .body("${analyzeConsumerProfile.response}")
	}

	handler h1 {
		on WORKFLOW_SCHEDULED
		when workflowNameEqualsTo("newConsumerProcess")
		then startTask("createNewConsumer")
	}
	handler h2 {
		on TASK_COMPLETED
		when taskNameEqualsTo("createNewConsumer")
		then startTask("analyzeConsumerProfile")
	}
	handler h3 {
		on TASK_COMPLETED
		when taskNameEqualsTo("analyzeConsumerProfile")
		then startTask("sendWelcomeEmail"),
	          startTask("sendWelcomePackage"),
	          startTask("sendAccountCardAndPassword")
	}
}
```

