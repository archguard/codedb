# 6. keep register metadata only

Date: 2023-02-11

## Status

2023-02-11 proposed

## Context

In PoC of registry we had saved `.jar` files, but it was not a good idea, because it was not possible to use it in CI/CD pipeline.

## Decision

So, we decided to save only metadata, like `action.json`, then we can choose different ways for our action

- download `.jar` file from maven repository
- download `.jar` file from GitHub release
- download `.jar` file from GitHub package registry
- download `.jar` file from any other place

## Consequences

Consequences here...
