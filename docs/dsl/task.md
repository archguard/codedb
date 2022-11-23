# Pipeline DSL

Sample Task Call

```groovy
task("loc") {
    // construct vars
    inputs = [files("src"), ]
    outputs = dir("build/loc")
}
```

### Presto SQL Task

[presto-parser](https://github.com/prestodb/presto/tree/master/presto-parser)

[https://github.com/querydsl/querydsl](https://github.com/querydsl/querydsl)

```java
List<Tuple> tuples = queryFactory.select(
        person.lastName, person.firstName, person.yearOfBirth)
        .from(person)
        .fetch();
```

