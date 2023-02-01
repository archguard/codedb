package org.archguard.codedb.factor.governance.metric

import javax.persistence.*

/**
 * Metric is a class that represents a metric.
 * - Metric Label is a label of a metric.
 * - Metric Name is a unique name of a metric.
 * - Metric Definition is a description of the metric.
 *
 * for examples:
 *
 * | Label | Metric Name          | Definition                                                                                    |
 * |-------|----------------------|-----------------------------------------------------------------------------------------------|
 * | LOC   | Lines of Code        | The number of lines of code of an operation or of a class, including blank lines and comments.|
 * | CYCLO | Cyclomatic Complexity| The maximum number of linearly independent paths in a method.                                 |
 *
 */
@Entity
class MetricDef(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    val name: String = "",
    val label: String = "",
    val definition: String = ""
) {

}

/**
 * Metric Value is a class that represents a metric value.
 */
class MetricValue(
    val name: String,
    val value: Double
)
