package org.archguard.codedb.factor.quality

import org.archguard.codedb.factor.governance.metric.MetricDef

import javax.persistence.*

/**
 * Coverage is a measure of the amount of code that is covered by tests.
 * It is a measure of the quality of the tests.
 *
 * samples:
 *
 * | fileName | trackedLines | coveredLines | uncoveredLines | partial | coverage |  metricDef |
 * |----------|--------------|--------------|----------------|---------|----------|------------|
 * | file1    | 100          | 80           | 20             | 0       | 80%      |            |
 * | file2    | 200          | 100          | 100            | 0       | 50%      |            |
 *
 */
@Entity
class Coverage(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    val fileName: String = "",
    val trackedLines: Int = 0,
    val coveredLines: Int = 0,
    val uncoveredLines: Int = 0,
    val partial: Int = 0,
    val coverage: Double = 0.0,
    @Column(name = "metric_def", nullable = false)
    val metricDef: MetricDef = MetricDef(null, "FileCoverage", "File Coverage", "File Coverage")
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
