# Ast Query


## TreeSitter

https://tree-sitter.github.io/tree-sitter/syntax-highlighting#queries

```schema
(source_file
  (function_declaration
    name: (identifier)
    parameters: (parameter_list
      (parameter_declaration
        name: (identifier)
        type: (type_identifier)))
    result: (type_identifier)
    body: (block
      (return_statement
        (expression_list
          (binary_expression
            left: (identifier)
            right: (int_literal)))))))
```


## kotlisp

https://github.com/absurdhero/kotlisp/blob/master/src/main/kotlin/net/raboof/kotlisp/SExpression.kt

```kotlin

import net.raboof.kotlisp.builtins.Builtin

data class SExpression(val exprs: List<Expr>) : Expr {
    companion object {
        val Empty = SExpression(emptyList())
    }

    override fun print(): String {
        return "(${exprs.map { it.print() }.joinToString(" ")})"
    }

    override fun toString(): String {
        return print()
    }

    override fun evaluate(env: ChainedEnvironment, denv: ChainedEnvironment): Expr {
        if (exprs.size == 0) {
            return QExpression.Empty
        }

        // first evaluate items from left to right
        val head = exprs.first().evaluate(env, denv)
        val rest = exprs.subList(1, exprs.size).map { it.evaluate(env, denv) }

        return when (head) {
            is Builtin -> {
                head.invoke(env, denv, rest)
            }
            is Lambda -> {
                head.invoke(denv, rest)
            }
            is Str, is Number, is Symbol, is True, is False, QExpression.Empty -> {
                if (rest.size > 0) {
                    throw IllegalArgumentException("cannot evaluate ${head.print()} as a function")
                }
                head
            }
            is SExpression -> {
                throw IllegalArgumentException("cannot evaluate s-expression ${head.print()} as a function")
            }
            else -> throw IllegalArgumentException("unknown result of expression type $head")
        }
    }

}
```

## Kotlin S Expression Sample

https://blog.jetbrains.com/kotlin/2014/04/kotlin-gets-support-for-s-expressions/


```
 (println (
    (lambda (arg1)
        (+
            "first lambda: "
            arg1
            (lambda (arg2)
                (+ "second lambda: " arg1 arg2)
            )
        )
    ) foo "bar"
))
```

## tiny-kotlin-lisp

https://github.com/komamitsu/tiny-kotlin-lisp

```bash
> (defun fib (n) (if (<= n 1) 1 (+ (fib (- n 1)) (fib (- n 2)))))

> (print (fib 37))
39088169
```