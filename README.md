# !!Experiment project

Architecture diagram

```
+------------------------------------------------------------+
|        +---------------+---------------+---------------+   |
|   UI   |   Workbench   | ArchComponent |   Code DSL    |   |
|        +---------------+---------------+---------------+   |
+------------------------------------------------------------+
|        +---------------+---------------+---------------+   |
| Domain | KotlinJupyter | FitnessEngine |  Task Engine  |   |
|        +---------------+---------------+---------------+   |
+------------------------------------------------------------+
|        +---------------+---------------+---------------+   |
| DataAI |  DataService  |    ML Algo    |    MongoDB    |   |
|        +---------------+---------------+---------------+   |
+------------------------------------------------------------+
```

# CodeDB for searching code snippets

Todos:

- [ ] TaskEngine
    - [ ] annotated DSL
    - [ ] Task API
        - [ ] Query Task
        - [ ] Index Task
    - [ ] Builtin Task
        - [ ] Loc
        - [ ] NoC
        - [ ] ...
    - [ ] Incremental Computing
- [ ] Fitness function Define
    - [ ] Math integration API
    - [ ] Data integration DSL
- [ ] DataService
    - feeder
        - [ ] test coverage
        - [ ] source code
        - [ ] security
        - [ ] sonarqube
        - [ ] ...
    - [ ] DSL Query
        - [ ] search by file name
        - [ ] search by ast
        - [ ] search by regex
        - [ ] search by code snippet
- [ ] Workbench
    - [ ] REPL environment
    - [ ] Kotlin Jupyter

Migrations:

- [ ] align APIs

## What is it?

CodeDB is a tool for searching code snippets.
It is a command line tool that can be used to search code snippets from a database of code snippets.
It is written in Kotlin and uses MongoDB as the database backend.

## Development

## Scan Data

test
cli: `java -jar/scanner_cli.jar --language=Kotlin --features=apicalls --output=http --output=json --path=. --server-url=http://localhost:8084`

### Install

1. setup JDK 17
2. install MongoDB and start it
    - follow: [https://www.mongodb.com/docs/manual/installation/](https://www.mongodb.com/docs/manual/installation/)
3. start spring boot application
    - `./gradlew bootRun`
