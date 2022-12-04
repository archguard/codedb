# Factor

Metric Factor is a metric for measuring the impact of a change on the codebase.

## Factor in Design

| Evolution     | Progress           | Goverance | Experience | DevOps               | Quality       | Productivity |
|---------------|--------------------|-----------|------------|----------------------|---------------|--------------|
| Coupling      | Tech Debt          | Document  | Developer  | Change Failure Rate  | Test Coverage | Tracing      |
| Hierarchy     | Path to Production | Gate      | User       | Deployment Frequency | Code Smell    | Performance  |
| Changeability | Linting            |           |            | Lead Time For Change |               | Cost         |
| Modifiability | Metric             |           |            | Mean Time To Restore |               |              |           

## Metric Matrix for Architecture Twin

| Metric | Description                                       | Formula                                    | Example                   |
|--------|---------------------------------------------------|--------------------------------------------|---------------------------|
| Factor | The number of files that are affected by a change | `factor = (files changed) / (total files)` | `factor = 2 / 100 = 0.02` |

## Expression by Explain

```kotlin
val coverage = "Changed Files" / "Total Files"
```
