# CodeDB: Technology Insight of Code

[![CI](https://github.com/archguard/codedb-poc/actions/workflows/ci.yaml/badge.svg)](https://github.com/archguard/codedb-poc/actions/workflows/ci.yaml)

![CodeDB Logo](assets/codedb.svg)

CodeDB is a Technology Insight Platform for Software Development.

> CodeDB
> 是一个专为软件开发本身开发的代码数据库，可以用于架构治理、代码生成等领域。基于[架构孪生](https://www.phodal.com/blog/architecture-twin/)
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

Code DataStructure

- server, which will provide the API
- client
    - CLI,
    - CLUI (experiment)
    - Gradle Plugin
- workflow-lib, which will be used by workflow
    - factor, foundation of fitness model
    - fitness-engine, define the basic element to calculate fitness
    - metric, common metric model of software
    - repl, kotlin REPL API
    - structure-ql, structure query language
    - task-core, the core of task engine
- feeder, which will fill the data into database
    - test coverage
    - source code
    - security
    - sonarqube
    - ...

## Principle

ThinSlice for Goal

By Level:

- define Goal
- custom Metric (Fitness function)
- create flow for task
- create job for task

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


