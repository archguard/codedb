package org.archguard.codedb.factor.progress

import org.archguard.codedb.factor.uuid
import javax.persistence.Entity
import javax.persistence.Id

/**
 * Path-to-production analysis is a technique for assessing and diagnosing any bottlenecks in the SDLC process.
 *
 */
@Entity
open class PathToProduction(
    @Id
    open val id: String = uuid(),
    open var name: String = "",
    open var steps: ArrayList<ProcessItem> = arrayListOf()
) {

}

/**
 * Sample: [https://www.thoughtworks.com/insights/articles/towards-a-secure-path-to-production](https://www.thoughtworks.com/insights/articles/towards-a-secure-path-to-production)
 *
 * Path to production is adapted from lean value stream mapping, which is used in the manufacturing industry to
 * improve processes in production throughput and analyze the cost of delay. The creation of a P2P is a task that
 * involves the whole team. Each and every activity in the context of product development is registered and brought
 * into a logical order.
 *
 * - People: Groups, Boards, Individuals
 * - Media: Meetings, Jour Fixe, Committees, Calls, eMails
 * - Deliverables: Reports, Analyses, User Stories, Commits, Containers, …
 * - Security controls alongside each step: Threat Modeling, Tests, Logging, Scanners, ...
 *
 */
@Entity
class SecurePathToProduction(
    @Id
    override val id: String = uuid(),
    override var name: String = "",
    override var steps: ArrayList<ProcessItem> = arrayListOf()
) : PathToProduction(id, name, steps) {

}


sealed class ProcessItem {
    class Discussions : ProcessItem()
    class Requirements : ProcessItem()
    class Analysis : ProcessItem()
    class Development : ProcessItem()
    class Testing : ProcessItem()
    class Deployment : ProcessItem()
}

