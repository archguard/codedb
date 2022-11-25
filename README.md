# CodeDB: database of code and software development.

>  CodeDB 是一个针为软件开发本身开发的代码数据库，可以用于架构治理、代码生成等领域。内建架构适度度函数、代码设计查询和存储 DSL、依赖分析引擎、机器学习算法等。

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

Multiple-platform Kotlin ?

# Roadmap

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
    - Builtin Data
        - [ ] Ast Tree with TreeSitter ??
        - [ ] Chapi API ??
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

# Query DSL

## S-Expression Query ?

like TreeSitter?

```ocaml
(package_declaration
	(scoped_identifier) @package-name)

(import_declaration
	(scoped_identifier) @import-name)

(program
    (class_declaration
	    name: (identifier) @class-name
        interfaces: (super_interfaces (interface_type_list (type_identifier)  @impl-name))?
        body: (class_body (method_declaration
            (modifiers
                (annotation
                  name: (identifier) @annotation.name
                      arguments: (annotation_argument_list)? @annotation.key_values
                )
            )?
            type: (type_identifier) @return-type
            name: (identifier) @function-name
            parameters: (formal_parameters (formal_parameter
              type: (type_identifier) @param-type
                name: (identifier) @param-name
            ))?
          ))?
    )
)
```

# Development

## Scan Data with ArchGuard

test
cli: `java -jar/scanner_cli.jar --language=Kotlin --features=apicalls --output=http --output=json --path=. --server-url=http://localhost:8084`

## Install

1. setup JDK 17
2. install MongoDB and start it
    - follow: [https://www.mongodb.com/docs/manual/installation/](https://www.mongodb.com/docs/manual/installation/)
3. start spring boot application
    - `./gradlew bootRun`
