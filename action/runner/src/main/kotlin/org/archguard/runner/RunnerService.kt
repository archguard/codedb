package org.archguard.runner

import org.archguard.runner.context.HostContext

open class RunnerService {
    protected val hostContext: HostContext = HostContext(hostType = "Runner")

    protected val trace = Tracing()

    init {
        trace.info("RunnerService initialized")
    }
}
