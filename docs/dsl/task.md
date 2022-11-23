# Pipeline DSL

## Fitness function

```kotlin
var fitness = { model, data -> 
    // model is a map of parameters
    // data is a map of data
    // return a map of fitness values
}
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

## Model Abstract

- [x] SourceCode => Chapi
- [ ] Version Management
  - Git
- [ ] APM
  - OpenTelemetry
  - Skywalking
  - Jaeger
  - Zipkin
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

