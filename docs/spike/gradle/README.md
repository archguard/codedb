# Gradle Notes


```groovy
def action = Mock(Action)
def provider = container.register("task", action)
provider.configure(action)
container.configureEach(action)
```