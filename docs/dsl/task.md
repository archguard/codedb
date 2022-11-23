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

```kotlin
class GitModel(val hash: String)
```

