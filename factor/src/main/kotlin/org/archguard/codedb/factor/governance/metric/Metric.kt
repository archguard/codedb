package org.archguard.codedb.factor.governance.metric

/**
 * Metric is a class that represents a metric.
 * - Metric Label is a label of a metric.
 * - Metric Name is a unique name of a metric.
 * - Metric Definition is a description of the metric.
 * - Metric Value is a value of the metric.
 *
 * for examples:
 *
 * | Label | Metric Name          | Definition                                                                                    |
 * |-------|----------------------|-----------------------------------------------------------------------------------------------|
 * | LOC   | Lines of Code        | The number of lines of code of an operation or of a class, including blank lines and comments.|
 * | CYCLO | Cyclomatic Complexity| The maximum number of linearly independent paths in a method.                                 |
 *
 */
class Metric(
    val name: String,
    val label: String,
    val definition: String,
    val value: List<MetricValue> = emptyList()
) {

}

class MetricValue(
    val name: String,
    val value: Double
)
