 # Pipeline DSL

## Fitness function

```kotlin
var fitness = { model, data -> 
    // model is a map of parameters
    // data is a map of data
    // return a map of fitness values
}
```

### Ranking Function 

```javascript
var ranking: HashMap<String, Range> = hashmapOf(
    "fitness" to 0.0..1.0,
    "accuracy" to 0.0..1.0,
    "precision" to 0.0..1.0,
    "recall" to 0.0..1.0,
    "f1" to 0.0..1.0
```

## Query

[presto-parser](https://github.com/prestodb/presto/tree/master/presto-parser)

[Presto MongoDB Connector](https://prestodb.io/docs/current/connector/mongodb.html)

[https://github.com/querydsl/querydsl](https://github.com/querydsl/querydsl)

```kotlin
var tuples: List<Tuple>  = queryFactory.select(
        person.lastName, person.firstName, person.yearOfBirth)
        .from(person)
        .fetch();
```

## Flow Engine for Task ?

```kotlin
@Parallel // for inputs ?
task("loc") {
    // construct vars
    inputs = [files("src"), ]
    outputs = dir("build/loc")
}
```

Git Hash ? 

```kotlin
// range
task("diff") {
    inputs = [git("HEAD~1..HEAD"), ]
    outputs = dir("build/diff")
}
```

### Database to DSL

```kotlin
var systemName = "systemid"

select("git") {
    start = "HEAD~1"
    end = "HEAD"

    systemId = select("system") {
        name = systemName
    }
}

// or
var data = query("select * from git where systemId = :systemId") {
    systemId = systemName
}
```

## Model Abstract

- [x] SourceCode => Chapi
- [ ] Version Management
  - Git
- [ ] APM
  - OpenTelemetry
    - OpenCensus/OpenTracing
  - Skywalking
  - Jaeger
  - Zipkin
- [ ] OS statistics
  - collectd
  - Prometheus
  - Grafana
  - statsd
- [ ] Quality
  - SonarQube
  - CodeClimate
  - Codecov
  - Coveralls
  - Codacy
- [ ] CI/CD
  - Jenkins
  - Travis
  - CircleCI
  - GitLab
  - Bamboo
  - TeamCity
  - GoCD
  - Drone
  - Buildkite
- [ ] Dependencies => SCA ?
  - DependencyCheck
  - OWASP Dependency-Check
  - DependencyBot
  - Snyk
- [ ] Container
  - Docker
  - Kubernetes
  - Docker Compose
- [ ] Security
  - OWASP
  - Sonatype
  - WhiteSource

```kotlin
class GitModel(val hash: String)
```

### Static Tracing API

like [Span](https://opencensus.io/tracing/span/)

> A span represents a single operation in a trace. A span could be representative of an HTTP request, a remote procedure call (RPC), a database query, or even the path that a code takes in user code, etc.

在 ArchGuard 构建中生成静态的 Tracing API，可以和动态的 Tracing API 结合起来，形成完整的 Tracing 数据。

![](https://opencensus.io/img/trace-trace.png)


### Metrics Data Model

[Metrics Data Model](https://opentelemetry.io/docs/reference/specification/metrics/data-model/)

## Job Engine ?

Cron Job

```kotlin
var cron = "0 0 12 * * ?"
var cron_parser = CronParser(cron)
class CronParser(str: String) {
    var cron = str
    var cron_expr = CronExpression(cron)
    fun next(): Date {
        return cron_expr.nextValidTimeAfter(Date())
    }
}
```

