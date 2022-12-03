package org.archguard.codedb.factor.quality

import org.archguard.codedb.factor.governance.metric.MetricDef

class Coverage(
    val fileName: String,
    val trackedLines: Int,
    val coveredLines: Int,
    val uncoveredLines: Int,
    val partial: Int,
    val coverage: Double,
    val metricDef: MetricDef = MetricDef("FileCoverage", "File Coverage", "File Coverage")
) {

}

enum class CounterEntity {
    INSTRUCTION,
    BRANCH,
    LINE,
    COMPLEXITY,
    METHOD,
    CLASS
}

enum class ElementType {
    GROUP,
    BUNDLE,
    PACKAGE,
    SOURCEFILE,
    CLASS,
    METHOD,
}