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