# Roadmap (TODO)

## 2023-07-01: 3.0-alpha

### 2023-04-01

- MVP

### 2023-07-01

- ArchGuard 2.x migration
    - Align ArchGuard APIs

## 2021-12-31

# Todos

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
