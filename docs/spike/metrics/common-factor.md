# Common Fitness Functions

or Common Architecture Metrics ?? or Metrics for Architecture Twin

> The fitness of a solution is a measure of how good it is. The fitness of a solution is usually a number,
but it can be any type of value. The fitness of a solution is usually calculated by a fitness function.
The fitness function is a function that takes a solution as input and returns a fitness value as output.

> [架构孪生](https://www.phodal.com/blog/architecture-twin/)是一种旨在精确反映架构设计、实现与运行态等的虚拟模型，以数字化的形式对软件的架构、代码模型、分层、实现技术等的进行动态的呈现。

三态：

- 设计态：目标架构。通过 DSL（领域特定语言） + 架构工作台来构建 。
- 开发态：实现架构。关注于：可视化 + 自定义分析 + 架构治理。
- 运行态：运行架构。结合 APM 工具，构建完整的分析链。

## M4AT: Metrics for Architecture Twin

 
### Metric: Quality of Architecture

Test Coverage: E2E/Unit/Integration/API ??

```kotlin
class FileCoverage(
    val fileName: String,
    val trackedLines: Int,
    val coveredLines: Int,
    val uncoveredLines: Int,
    val partial: Int,
    val coverage: Double
) : Metric {

}

enum class CounterEntity {
    INSTRUCTION,
    BRANCH,
    LINE,
    COMPLEXITY,
    METHOD,
    CLASS
}

enum class ElementType {
    GROUP,
    BUNDLE,
    PACKAGE,
    SOURCEFILE,
    CLASS,
    METHOD,
}
```

### Metrics: Security

- [ ] [OWASP Top 10](https://owasp.org/www-project-top-ten/)


### Open Tracing Model

OpenTelemetry modeling

### Code Smells

from SonarType ?


