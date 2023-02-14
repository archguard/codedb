package org.archguard.runner.runner

import org.archguard.action.core.Tracing
import org.archguard.runner.context.HostContext

open class RunnerService {
    protected val hostContext: HostContext = HostContext(hostType = "Runner")

    protected val trace = Tracing()

    init {
        trace.info("RunnerService initialized")
    }
}
