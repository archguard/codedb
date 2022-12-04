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

enum class ElementType(private val level: Int) {
    GROUP(0),
    BUNDLE(1),
    PACKAGE(2),
    SOURCEFILE(3),
    CLASS(4),
    METHOD(5),

    // todo: or call expression
    INSTRUCTION(6)
    ;

    fun level(): Int {
        return level
    }
}
