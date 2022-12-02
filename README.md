# CodeDB: database of code and software development.

[![CI](https://github.com/archguard/codedb-poc/actions/workflows/ci.yaml/badge.svg)](https://github.com/archguard/codedb-poc/actions/workflows/ci.yaml)

![CodeDB Logo](asssets/codedb.svg)

> CodeDB 是一个专为软件开发本身开发的代码数据库，可以用于架构治理、代码生成等领域。基于[架构孪生](https://www.phodal.com/blog/architecture-twin/)的理念，内建架构适度度函数、代码设计查询和存储 DSL、依赖分析引擎、机器学习算法等。

Architecture diagram

```
+------------------------------------------------------------+
|        +----------------+---------------+---------------+   |
|   UI   |    Workbench   | ArchComponent |   Query DSL   |   |
|        +----------------+---------------+---------------+   |
+------------------------------------------------------------+
|        +----------------+---------------+---------------+   |
| Domain | Kotlin Jupyter | FitnessEngine |  Task Engine  |   |
|        +----------------+---------------+---------------+   |
+------------------------------------------------------------+
|        +----------------+---------------+---------------+   |
| DataAI | MetricModeling |    ML Algo    |    MongoDB    |   |
|        +----------------+---------------+---------------+   |
+------------------------------------------------------------+
```

Multiple-platform Kotlin ?

# Roadmap

Model: Architecture Twin ?

Todos:

- [ ] Query DSL
    - [ ] MongoDB. Need to implement custom `querysql-mongodb` for new morphia.
- [ ] Query Engine
    - [ ] search by file name
    - [ ] search by ast
    - [ ] search by regex
    - [ ] search by code snippet
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
- [ ] Fitness Engine
    - [ ] Fitness function
    - [ ] Math integration API
    - [ ] Data integration DSL
- [ ] MetricModeling
    - Builtin Data
        - [ ] Ast Tree with TreeSitter ??
        - [ ] Chapi API ??
    - feeder
        - [ ] test coverage
        - [ ] source code
        - [ ] security
        - [ ] sonarqube
        - [ ] ...
- [ ] Workbench
    - [ ] REPL environment
    - [ ] Kotlin Jupyter
- [ ] AI Algorithm
    - [ ] ML Algorithm
        - [ ] KNN
        - [ ] SVM
        - [ ] ...
- [ ] Data Visualization
    - [ ] Landscape View
    - [ ] Mapping View
        - [ ] Services Map, Database Map, Message Map
    - [ ] Network Profiling
        - [ ] [Sample](https://user-images.githubusercontent.com/5441976/187399382-907788dc-9c03-4a66-a560-07d28fd2de07.png)
    - [ ] CodeCity
    - [ ] ...

Tech Stack Design:


[@react-three/fiber](https://github.com/pmndrs/react-three-fiber) is a React renderer for threejs. Build your scene declaratively with re-usable, self-contained components that react to state, are readily 
interactive and can participate in React's ecosystem.

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

1. setup JDK 11
2. install MongoDB and start it
    - follow: [https://www.mongodb.com/docs/manual/installation/](https://www.mongodb.com/docs/manual/installation/)
3. start spring boot application
    - `./gradlew bootRun`_
