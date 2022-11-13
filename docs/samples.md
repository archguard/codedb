# Syntax Design

## Fitness DSL 

```kotlin
val fitness = Fitness { x: Double -> x * x }
```

## Tasking

```kotlin
@Input
fun loc(context: Context): Int {
    
}
```

or

```kotlin
@CodeTask
class Loc : DefaultTask {
    /// 
    @Input
    @Incremental
    fun query(datasource: Datasource) {
        
    }
    
    @Internal
    fun loc(context: Context): Int {
        
    }

    @Output 
    //@OutputData => to database ?
    fun save(context: Context): String {
        
    }

    @TaskAction
    fun taskAction() {
        
    }
}
```