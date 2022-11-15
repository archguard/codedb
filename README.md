# !!Experiment project

Architecture diagram

```
+----------------------------------------------------------------+
|        +---------------+---------------+---------------+       |
|   UI   |   Workbench   | UI Components |    Task DSL   |       |
|        +---------------+---------------+---------------+       |
+----------------------------------------------------------------+
|        +---------------+---------------+---------------+       |
| Domain | KotlinJupyter |  Task Engine  | FitnessEngine |       |
|        +---------------+---------------+---------------+       |
+----------------------------------------------------------------+
|        +---------------+---------------+---------------+       |
| DataAI |  DataService  |    ML Algo    |    MongoDB    |       |
|        +---------------+---------------+---------------+       |
+----------------------------------------------------------------+
```

# CodeDB for searching code snippets 

Todos:

- [ ] Fitness function Define
  - [ ] Math integration
  - [ ] Data integration
- [ ] Incremental Computing Tasks
  - [ ] annotated DSL
  - [ ] Builtin Task
    - [ ] Query Task
    - [ ] Index Task
- [ ] DSL Query
  - [ ] search by file name
  - [ ] search by ast
  - [ ] search by regex
- [ ] feeder  
  - [ ] test coverage
  - [ ] source code
  - [ ] security
  - [ ] sonarqube
  - [ ] ...
- [ ] align APIs
- [ ] REPL environment

## What is it?

CodeDB is a tool for searching code snippets. 
It is a command line tool that can be used to search code snippets from a database of code snippets.
It is written in Kotlin and uses MongoDB as the database backend.



## Development

## Scan Data

test cli: `java -jar/scanner_cli.jar --language=Kotlin --features=apicalls --output=http --output=json --path=. --server-url=http://localhost:8084`

### Install

1. setup JDK 17
2. install MongoDB and start it
   - follow: [https://www.mongodb.com/docs/manual/installation/](https://www.mongodb.com/docs/manual/installation/)
3. start spring boot application
   - `./gradlew bootRun`
