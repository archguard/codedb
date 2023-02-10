package org.archguard.runner

open class RunnerService {
    protected val hostContext: HostContext = HostContext()

    protected val trace = Tracing()

    init {
        trace.info("RunnerService initialized")
    }
}
