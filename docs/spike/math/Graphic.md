# Graphics


## Graphviz

```kotlin
implementation("guru.nidi:graphviz-kotlin:0.18.1")
```

## Kaliningraph: A Type Family of Algebraic Graphs

[https://github.com/breandan/galoisenne](https://github.com/breandan/galoisenne)

## Lets-Plot for Kotlin

https://github.com/JetBrains/lets-plot-kotlin

Lets-Plot for Kotlin is a Kotlin API for the Lets-Plot library - an open-source plotting library for statistical data.

Lets-Plot Kotlin API is built on the principles of layered graphics first described in the
Leland Wilkinson work The Grammar of Graphics and later implemented in the ggplot2 package for R.


```kotlin
val rand = java.util.Random()
val data = mapOf (
    "rating" to List(200) { rand.nextGaussian() } + List(200) { rand.nextGaussian() * 1.5 + 1.5 },
    "cond" to List(200) { "A" } + List(200) { "B" }
)

var p = letsPlot(data)
p += geomDensity(color="dark_green", alpha=.3) {x="rating"; fill="cond"}
p + ggsize(700, 350)
```

