# QueryDSL


两种模式？

1. 基于 QueryDSL 对内建的选择器进行扩展，`query.where()` 之类的
2. 基于普通的 SQL 语句字符串？
3. 基于 Presto 构建表达器？

## QueryDSL MongoDB

```kotlin
val list: List<CodeDocument> = query
        .where(CodeDocument().systemId.eq("Bob"))
        .fetch()
```

Bean Generator


```java
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {
    public final StringPath name = createString("name");
}

public class BeanPath<T> extends SimpleExpression<T> implements Path<T> {
    protected StringPath createString(String property) {
        return add(new StringPath(forProperty(property)));
    }
}

public class StringPath extends StringExpression implements Path<String> {
    protected StringPath(PathMetadata metadata) {
        super(ExpressionUtils.path(String.class, metadata));
        this.pathMixin = (PathImpl<String>) mixin;
    }
}

public abstract class StringExpression extends LiteralExpression<String> {
    
}

public abstract class LiteralExpression<T extends Comparable> extends ComparableExpression<T> {

}

public abstract class ComparableExpression<T extends Comparable> extends ComparableExpressionBase<T> {
    
}

public abstract class ComparableExpressionBase<T extends Comparable> extends SimpleExpression<T> {
    /**
     * Create a {@code this == right} expression
     *
     * <p>Use expr.isNull() instead of expr.eq(null)</p>
     *
     * @param right rhs of the comparison
     * @return this == right
     */
    public BooleanExpression eq(T right) {
        if (right == null) {
            throw new IllegalArgumentException("eq(null) is not allowed. Use isNull() instead");
        } else {
            return eq(ConstantImpl.create(right));
        }
    }
}
```

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