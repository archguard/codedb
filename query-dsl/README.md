# QueryDSL


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