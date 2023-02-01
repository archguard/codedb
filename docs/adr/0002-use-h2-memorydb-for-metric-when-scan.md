# 2. use h2 memorydb for metric when scan

Date: 2023-02-02

## Status

2023-02-02 proposed

## Context

In ArchGuard 1.x and 2.x, we use MySQL to build the metric database.

- It is not easy to use MySQL in the scan process.
- The old scanner2 module will be removed in the future, but we want to keep the compatibility for old code.

So we decide to use h2 memorydb to build the metric database.

Here is the modules: `MetricPersistApplService`
                                               
- DitService
- LCOM4Service
- NocService
- ScannerDataClassService
- JClassRepository
- JMethodRepository
- FanInFanOutService
- ScannerCircularDependencyService
- ClassMetricRepository
- MethodMetricRepository
- PackageMetricRepository
- ModuleMetricRepository
- DataClassRepository
- ScannerCircularDependencyMetricRepository

## Decision

Decision here...

## Consequences

Consequences here...
