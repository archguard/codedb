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
