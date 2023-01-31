# CodeDB: Technology Insight of Code

[![CI](https://github.com/archguard/codedb-poc/actions/workflows/ci.yaml/badge.svg)](https://github.com/archguard/codedb-poc/actions/workflows/ci.yaml)

![CodeDB Logo](assets/codedb.svg)

CodeDB is a Technology Insight Platform for Software Development.

> CodeDB
>
是一个专为软件开发本身开发的代码数据库，可以用于架构治理、代码生成等领域。基于[架构孪生](https://www.phodal.com/blog/architecture-twin/)
> 的理念，内建架构适度度函数、代码设计查询和存储 DSL、依赖分析引擎、机器学习算法等。

Architecture diagram:

```
+------------------------------------------------------------+
|        +----------------+---------------+---------------+   |
|   UI   |    Workbench   | ArchComponent |     eDSL      |   |
|        +----------------+---------------+---------------+   |
+------------------------------------------------------------+
|        +----------------+---------------+---------------+   |
| Domain |   Kotlin REPL  | FitnessEngine |  Task Engine  |   |
|        +----------------+---------------+---------------+   |
+------------------------------------------------------------+
|        +----------------+---------------+---------------+   |
| DataAI |  Factor Model  | Structure QL  |    ML Algo    |   |
|        +----------------+---------------+---------------+   |
+------------------------------------------------------------+
```

# Roadmap

Todos:

- [ ] Workbench
    - [x] REPL environment
        - [x] Kotlin REPL
        - [x] Kotlin Jupyter Kernel
        - [x] Dynamic Library
    - [ ] REPL Editor
- [ ] eDSL = embedding DSL
    - [x] MongoDB. Need to implement custom `querysql-mongodb` for new morphia.
    - [x] Query DSL
    - [ ] Data integration DSL
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
- [ ] Factor Model. CodeDB 自带模型，用于查询代码库的度量指标
    - Builtin Data
        - [ ] Ast Tree with TreeSitter ??
        - [ ] Chapi API ??
    - feeder
        - [ ] test coverage
        - [ ] source code
        - [ ] security
        - [ ] sonarqube
        - [ ] ...
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
- [ ] Data Query
    - [ ] Structure QL. 用于支持动态的模型引入
        - [ ] search by file name
        - [ ] search by ast
        - [ ] search by regex
    - [ ] Query Engine
        - [ ] search by code snippet

Migrations:

- [ ] align APIs

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

## Principle

ThinSlice for Goal

By Level Goal -> Flow/Fitness -> Task -> Job

## Scan Data with ArchGuard

test
cli: `java -jar/scanner_cli.jar --language=Kotlin --features=apicalls --output=http --output=json --path=. --server-url=http://localhost:8084`

## Install

1. setup JDK 11
2. install MongoDB and start it
    - follow: [https://www.mongodb.com/docs/manual/installation/](https://www.mongodb.com/docs/manual/installation/)
3. start spring boot application
    - `./gradlew bootRun`_

### Known Issues

#### Why JDK 11 with MongoDB 5.0

问题：

1. cglib 不支持 Java 17 Java 16 and 17 compatibility #191 : https://github.com/cglib/cglib/issues/191
2. QueryDSL MongoDB 5.0 只支持 MongoDB 6.0 以下版本（暂不支持 6.0），需要自己实现 querysql-mongodb。


