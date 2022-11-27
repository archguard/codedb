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


