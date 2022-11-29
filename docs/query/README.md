# Query

## Presto

https://github.com/prestodb/presto

> Presto is a distributed SQL query engine for big data.


## Velox

https://github.com/facebookincubator/velox

> Velox is a C++ database acceleration library which provides reusable, extensible, and high-performance data processing 
> components. These components can be reused to build compute engines focused on different analytical workloads, 
> including batch, interactive, stream processing, and AI/ML. Velox was created by Facebook and it is currently
> developed in partnership with Intel, ByteDance, and Ahana.


- Expression Eval: a fully vectorized expression evaluation engine that allows expressions to be efficiently executed on top of Vector/Arrow encoded data.

## QueryDSL Mongodb

http://querydsl.com/static/querydsl/latest/reference/html/ch02s07.html

```java
Morphia morphia;
Datastore datastore;
// ...
QUser user = new QUser("user");
MorphiaQuery<User> query = new MorphiaQuery<User>(morphia, datastore, user);
List<User> list = query
    .where(user.firstName.eq("Bob"))
    .fetch();
```
