# Structure QL

两种模式

1. 基于 QueryDSL 对内建的选择器进行扩展，`query.where()` 之类的
2. 基于普通的 SQL 语句字符串
3. 基于 Presto 构建表达器（未来）

## KMongo

https://litote.org/kmongo/

```kotlin
data class Jedi(val name: String, val age: Int)

col.insertOne(Jedi("Luke Skywalker", 19)) //java driver method
col.insertOne("{name:'Yoda',age:896}")    //KMongo extension function

//object mapping & mongo shell query format is supported ->
val yoda : Jedi? = col.findOne("{name: {$regex: 'Yo.*'}}")

//type-safe query style is recommended for complex apps ->
val luke = col.aggregate<Jedi>(
            match(Jedi::age lt yoda?.age),
            sample(1)
        ).first()   
```

