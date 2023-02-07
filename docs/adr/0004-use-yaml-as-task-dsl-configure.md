# 4. use yaml as task DSL configure

Date: 2023-02-07

## Status

2023-02-07 proposed

2023-02-07 accepted

## Context

In our task engine, we need an orchestration DSL to define the task flow. We need to define the task flow in a DSL, 
and then we can use the DSL to orchestrate the task flow.

Design a DSL is not easy, we need to consider the following things (Generate by GitHub Copilot):

- How to define the task flow?
- How to define the task?
- How to define the task dependency?
- How to define the task parameter?
- How to define the task output?
- How to define the task input?
- How to define the task error?
- How to define the task retry?
- How to define the task timeout?
- ...

We also need to implement the DSL and the IDE plugin. It's a lot of work. For the coding, it's easy to implement, but for the user, 
they need to learn the DSL. It's not easy to learn a DSL.

So we need to find a way to reduce the learning cost, like Jenkinsfile, we can use the Groovy as the DSL. But Groovy is not a human-readable data-serialization language.

## Decision

So, we can use the YAML as the DSL, and it has used by GitHub Actions, so it's easy to learn and use.

## Consequences

1. We can use the YAML as the task DSL.
2. use Kotlin Multiplatform to implement the DSL.
3. use Json Schema to validate the DSL.

