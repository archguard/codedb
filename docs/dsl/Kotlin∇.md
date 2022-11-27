# Kotlin∇: Type-safe Symbolic Differentiation for the JVM

https://github.com/breandan/kotlingrad

"Programming tools for intelligent systems" - 


```kotlin
"d(sin x)/dx should be equal to (sin(x + dx) - sin(x)) / dx" {
  NumericalGenerator.assertAll { ẋ ->
    val f = sin(x)
    
    val `df∕dx` = d(f) / d(x)
    val adEval = `df∕dx`(ẋ) 
    
    val dx = 1E-8
    // Since ẋ is a raw numeric type, sin => kotlin.math.sin
    val fdEval = (sin(ẋ + dx) - sin(ẋ)) / dx
    adEval shouldBeApproximately fdEval
  }
}
```

## Kaliningraph

这个库实现了一个新的计算模型，我们称之为图形计算。 与之前的工作（例如图灵机和 Church 的 λ 演算）相比，该模型的优势在于它可以直接转换为 
GPU 上的迭代矩阵乘法，并且具有许多理想的代数性质。 此外，它提供了一种自然的方式来表达代数电路、神经网络、因子图、证明网络，并且与编程语言理论、
自动机理论和范畴论有许多联系。

```kotlin
val de = LabeledGraph { d - e }
val dacbe = LabeledGraph { d - a - c - b - e }
val dce = LabeledGraph { d - c - e }

val abcd = LabeledGraph { a - b - c - d }
val cfde = LabeledGraph { c - "a" - f - d - e }

val dg = LabeledGraph(dacbe, dce, de) + Graph(abcd, cfde)
dg.show()
```

![](https://github.com/breandan/galoisenne/raw/master/latex/figures/visualization.svg)

Computation graph

```kotlin
Notebook {
  a = b + c
  f = b - h
}.show()
```

![](https://github.com/breandan/galoisenne/raw/master/pdg_demo.svg)

